package org.springframework.roo.clinictests.web;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.clinictests.domain.Pet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * = PetsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.ITEM)
@RooJSON
public class PetsItemJsonController {


  /**
   * TODO Auto-generated method documentation
   * 
   * @param pet
   * @return ResponseEntity
   */
  @GetMapping(name = "show")
  public ResponseEntity<?> show(@ModelAttribute Pet pet) {
    return ResponseEntity.ok(pet);
  }

}
