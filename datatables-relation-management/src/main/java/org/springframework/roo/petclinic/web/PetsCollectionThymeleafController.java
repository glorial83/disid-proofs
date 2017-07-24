package org.springframework.roo.petclinic.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.ConvertedDatatablesData;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesColumns;
import io.springlets.data.web.datatables.DatatablesPageable;

/**
 * = PetsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class PetsCollectionThymeleafController {

  /**
   * Returns all the pets that are not assigned yet to the provided Owner
   * 
   * @param datatablesColumns
   * @param search
   * @param pageable
   * @param draw
   * @return ResponseEntity
   */
  @GetMapping(produces = Datatables.MEDIA_TYPE, name = "datatablesNotAssignedToOwner",
      value = "/dtNotAssignedToOwner/{owner}")
  @ResponseBody
  public ResponseEntity<ConvertedDatatablesData<Pet>> datatablesNotAssignedToOwner(
      @ModelAttribute Owner owner, DatatablesColumns datatablesColumns, GlobalSearch search,
      DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    Page<Pet> pets = getPetService().findAllNotAssignedToOwner(owner, search, pageable);
    long totalPetsCount = pets.getTotalElements();
    if (search != null && StringUtils.isNotBlank(search.getText())) {
      totalPetsCount = getPetService().count();
    }
    ConvertedDatatablesData<Pet> datatablesData = new ConvertedDatatablesData<Pet>(pets,
        totalPetsCount, draw, getConversionService(), datatablesColumns);
    return ResponseEntity.ok(datatablesData);
  }

}
