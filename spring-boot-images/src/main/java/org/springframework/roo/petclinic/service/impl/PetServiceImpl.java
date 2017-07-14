package org.springframework.roo.petclinic.service.impl;

import io.springlets.data.jpa.domain.EmbeddableImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.ImageService;
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

  @Autowired
  private ImageService imageService;

  /**
   * Method that saves the provided {@link Pet}
   * 
   * @param entity
   * @return Pet
   */
  @Transactional
  public Pet save(Pet pet, EmbeddableImage newImage) {

    // Save the new image
    if (newImage != null) {
      EmbeddableImage formattedAndResizedImage = newImage.formatAndResize("png", 150, 150);
      // Resize and format the image
      // Set the resized and formatted image as new pet image
      Image resizedImage = new Image(formattedAndResizedImage);
      // Saving the formatted image
      Image savedImage = imageService.save(resizedImage);

      // Set the new created image to the pet that will be created
      pet.setImage(savedImage);
    }
    // Delegates into the Pet Repository to save the entity 
    return getPetRepository().save(pet);
  }

  @Transactional
  public Pet save(Pet pet) {
    return getPetRepository().save(pet);
  }

}
