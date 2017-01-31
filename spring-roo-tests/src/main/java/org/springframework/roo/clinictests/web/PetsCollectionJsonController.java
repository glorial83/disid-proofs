package org.springframework.roo.clinictests.web;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.clinictests.domain.Pet;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * = PetsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.COLLECTION)
@RooJSON
public class PetsCollectionJsonController {

  /**
   * TODO Auto-generated method documentation
   * 
   * @param globalSearch
   * @param pageable
   * @return ResponseEntity
   */
  @GetMapping(name = "list")
  public ResponseEntity<Page<Pet>> list(GlobalSearch globalSearch, Pageable pageable) {

    Page<Pet> pets = getPetService().findAll(globalSearch, pageable);
    return ResponseEntity.ok(pets);
  }
}
