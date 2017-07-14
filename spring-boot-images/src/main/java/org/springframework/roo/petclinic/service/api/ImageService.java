package org.springframework.roo.petclinic.service.api;

import org.springframework.roo.addon.layers.service.annotations.RooService;
import org.springframework.roo.petclinic.domain.Image;

import java.util.List;

/**
 * = ImageService
 *
 * Image service that manages the service entity
 */
@RooService(entity = Image.class)
public interface ImageService {

  /**
   * TODO Auto-generated method documentation
   * 
   * @param entity
   * @return Pet
   */
  public abstract Image save(Image image);

  /**
   * Method that returns all the images
   * 
   * @return
   */
  public abstract List<Image> findAll();

  /**
   * Method that returns the requested image
   * 
   * @param id
   * @return
   */
  public abstract Image findOne(Long id);

}
