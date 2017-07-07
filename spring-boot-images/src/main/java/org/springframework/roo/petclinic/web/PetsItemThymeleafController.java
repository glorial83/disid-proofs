package org.springframework.roo.petclinic.web;

import org.apache.commons.codec.binary.Base64;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;


/**
 * = PetsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.ITEM)
@RooThymeleaf
public class PetsItemThymeleafController {

  /**
   * Method that returns the show view of the entity Pet
   * 
   * @param pet
   * @param model
   * @return ModelAndView
   */
  @GetMapping(name = "show")
  public ModelAndView show(@ModelAttribute Pet pet, Model model) {
    model.addAttribute("pet", pet);

    // Include into the model the necessary information to show the image
    byte[] imageBytes = pet.getImage();
    try {
      String contentType =
          URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
      model.addAttribute("imageType", contentType);
      model.addAttribute("imageBase64", Base64.encodeBase64String(imageBytes));
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

    return new ModelAndView("pets/show");
  }
}
