package org.springframework.roo.petclinic.service.api;
import io.springlets.data.jpa.domain.EmbeddableImage;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.petclinic.domain.Pet;

/**
 * = PetService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Pet.class)
public interface PetService {
  
  /**
   * TODO Auto-generated method documentation
   * 
   * @param entity
   * @return Pet
   */
  public abstract Pet save(Pet entity, EmbeddableImage newImage);
  
}
