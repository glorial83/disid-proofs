package org.springframework.roo.petclinic.service.impl;

import io.springlets.data.jpa.domain.EmbeddableImage;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.PetService;
import org.springframework.transaction.annotation.Transactional;

/**
 * = PetServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PetService.class)
public class PetServiceImpl implements PetService {

  /**
   * Method that saves the provided {@link Pet}
   * 
   * @param entity
   * @return Pet
   */
  @Transactional
  public Pet save(Pet pet) {
    // Resize and format the image
    EmbeddableImage formattedAndResizedImage = pet.getImage().formatAndResize("png", 1000, 1000);
    // Set the resized and formatted image as new pet image
    pet.setImage(formattedAndResizedImage);
    // Delegates into the Pet Repository to save the entity 
    return getPetRepository().save(pet);
  }

}
