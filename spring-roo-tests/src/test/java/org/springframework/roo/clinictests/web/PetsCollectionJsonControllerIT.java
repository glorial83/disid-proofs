/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.roo.clinictests.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.config.EnableSpringletsDataWebSupport;
import io.springlets.http.converter.json.BindingResultModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.roo.clinictests.dod.PetFactory;
import org.springframework.roo.clinictests.domain.Pet;
import org.springframework.roo.clinictests.service.api.PetService;
import org.springframework.roo.clinictests.validation.CollectionValidator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

/**
 * Integration tests for the {@link PetsCollectionJsonController} class.
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PetsCollectionJsonController.class, secure = false)
@EnableSpringDataWebSupport
@EnableSpringletsDataWebSupport
@Import({BindingResultModule.class, CollectionValidator.class})
public class PetsCollectionJsonControllerIT {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PetService petService;

  private PetFactory factory = new PetFactory();

  @Captor
  private ArgumentCaptor<Collection<Pet>> petCollectionCaptor;

  @Captor
  private ArgumentCaptor<Iterable<Long>> petIdIterableCaptor;

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void listShouldReturnPets() throws Exception {
    // Setup
    Pet pet0 = factory.create(0);
    Pet pet1 = factory.create(1);
    Page<Pet> page = new PageImpl<Pet>(Arrays.asList(pet0, pet1));
    when(petService.findAll(any(GlobalSearch.class), any(Pageable.class))).thenReturn(page);

    // For information about the JsonPath syntax take a look at
    // the README.adoc at: https://github.com/jayway/JsonPath

    // Execute & Verify
    // @formatter:off
    mvc.perform(get("/pets")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.totalElements", is(2)))
        .andExpect(jsonPath("$.last", is(true)))
        .andExpect(jsonPath("$.numberOfElements", is(2)))
        .andExpect(jsonPath("$.content[0].id", is(pet0.getId())))
        .andExpect(jsonPath("$.content[0].name", is(pet0.getName())))
        .andExpect(jsonPath("$.content[0].type", is(pet0.getType().toString())))
        .andExpect(jsonPath("$.content[0].weight", is(pet0.getWeight().doubleValue())))
        .andExpect(jsonPath("$.content[1].id", is(pet1.getId())))
        .andExpect(jsonPath("$.content[1].name", is(pet1.getName())))
        .andExpect(jsonPath("$.content[1].type", is(pet1.getType().toString())))
        .andExpect(jsonPath("$.content[1].weight", is(pet1.getWeight().doubleValue())));
        //.andDo(print());
    // @formatter:on
  }

  @Test
  public void createShouldCreateANewPetAndReturnUriToGetIt() throws Exception {
    // Setup
    // Sent pet can't have the id set
    Pet petRequest = factory.create(1);
    String jsonContent = mapper.writeValueAsString(petRequest);

    Pet petResponse = factory.create(1);
    petResponse.setId(1l);
    when(petService.save(any(Pet.class))).thenReturn(petResponse);

    // Execute & Verify
    // @formatter:off
    mvc.perform(post("/pets")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/pets/1/"));
    // @formatter:on
  }

  @Test
  public void createWithIdShouldNotBeAllowed() throws Exception {
    // Setup
    // Sent pet can't have the id set
    Pet petRequest = factory.create(1);
    petRequest.setId(1l);
    String jsonContent = mapper.writeValueAsString(petRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(post("/pets")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());
    // @formatter:on
  }

  @Test
  public void createWithNotValidPetShouldNotBeAllowed() throws Exception {
    // Setup
    // Sent pet can't have the id set
    Pet petRequest = factory.create(1);
    petRequest.setWeight(-1.0f);;
    String jsonContent = mapper.writeValueAsString(petRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(post("/pets")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errors.weight", is("must be greater than or equal to 0")));
    // @formatter:on
  }

  @Test
  public void createBatchShouldCreateNewPetsAndReturnUriToList() throws Exception {
    // Setup
    Pet[] petsRequest = new Pet[] {factory.create(0), factory.create(1), factory.create(2)};
    String jsonContent = mapper.writeValueAsString(petsRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(post("/pets/batch")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/pets/"));
    // @formatter:on

    verify(petService).save(petCollectionCaptor.capture());
    // NOTE: requires equals method implemented in the Pet class, ignoring date values
    // @formatter:off
    assertThat(petCollectionCaptor.getValue())
        .as("Check the pets to save are the same as the sent ones")
        .containsExactly(petsRequest);
    // @formatter:on
  }

  @Test
  public void createBatchWithNotValidPetShouldNotBeAllowed() throws Exception {
    // Setup
    Pet badPet = factory.create(1);
    badPet.setWeight(-1.0f);
    Pet[] petsRequest = new Pet[] {factory.create(0), badPet, factory.create(2)};
    String jsonContent = mapper.writeValueAsString(petsRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(post("/pets/batch")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errors.1.weight", is("must be greater than or equal to 0")));
    // @formatter:on
  }

  @Test
  public void updateBatchShouldUpdatePetsAndReturnUriToList() throws Exception {
    // Setup
    Pet[] petsRequest = new Pet[] {factory.create(0), factory.create(1), factory.create(2)};
    petsRequest[0].setId(0l);
    petsRequest[1].setId(1l);
    petsRequest[2].setId(2l);
    String jsonContent = mapper.writeValueAsString(petsRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(put("/pets/batch")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    // @formatter:on

    verify(petService).save(petCollectionCaptor.capture());
    // NOTE: requires equals method implemented in the Pet class, ignoring date values
    // @formatter:off
    assertThat(petCollectionCaptor.getValue())
        .as("Check the pets to save are the same as the sent ones")
        .containsExactly(petsRequest);
    // @formatter:on
  }

  @Test
  public void updateBatchWithNotValidPetShouldNotBeAllowed() throws Exception {
    // Setup
    Pet badPet = factory.create(1);
    badPet.setId(1l);
    badPet.setWeight(-1.0f);
    Pet[] petsRequest = new Pet[] {factory.create(0), badPet, factory.create(2)};
    String jsonContent = mapper.writeValueAsString(petsRequest);

    // Execute & Verify
    // @formatter:off
    mvc.perform(put("/pets/batch")
        .content(jsonContent)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errors.1.weight", is("must be greater than or equal to 0")));
    // @formatter:on
  }

  @Test
  public void deleteBatchShouldUpdatePetsAndReturnUriToList() throws Exception {
    // Setup
    String uri = "/pets/batch/1,2";

    // Execute & Verify
    // @formatter:off
    mvc.perform(delete(uri)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    // @formatter:on
    verify(petService).delete(petIdIterableCaptor.capture());
    // @formatter:off
    assertThat(petIdIterableCaptor.getValue())
        .as("Check the pet ids to delete are the same as the sent ones")
        .containsExactly(1l,2l);
    // @formatter:on
  }
}
