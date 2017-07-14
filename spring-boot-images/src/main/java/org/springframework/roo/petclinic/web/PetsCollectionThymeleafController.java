package org.springframework.roo.petclinic.web;

import io.springlets.data.jpa.domain.EmbeddableImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.roo.petclinic.service.api.ImageService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import java.util.List;

import javax.validation.Valid;

/**
 * = PetsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class PetsCollectionThymeleafController {

  @Autowired
  private ImageService imageService;

  /**
   * TODO Auto-generated method documentation
   * 
   * @param pet
   * @param result
   * @param model
   * @return ModelAndView
   */
  @PostMapping(name = "create")
  public ModelAndView create(@Valid @ModelAttribute Pet pet, BindingResult result,
      @RequestParam(name = "newImage") EmbeddableImage newImage, Model model) {
    if (result.hasErrors()) {
      populateForm(model);

      return new ModelAndView("/pets/create");
    }
    Pet newPet = getPetService().save(pet, newImage);
    UriComponents showURI =
        getItemLink().to(PetsItemThymeleafLinkFactory.SHOW).with("pet", newPet.getId()).toUri();
    return new ModelAndView("redirect:" + showURI.toUriString());
  }

  @GetMapping(path = "/images", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public ResponseEntity<List<Image>> findAllImages() {
    List<Image> images = imageService.findAll();
    return ResponseEntity.ok(images);
  }

}
