package org.springframework.roo.entityformat.web;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.ConvertedDatatablesData;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesColumns;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooDetail;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.entityformat.domain.Owner;
import org.springframework.roo.entityformat.domain.Pet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * = OwnersItemPetsThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "pets")
@RooThymeleaf
public class OwnersItemPetsThymeleafController {

  @Autowired
  private ConversionService conversionService;

  /**
   * TODO Auto-generated method documentation
   *
   * @param owner
   * @param search
   * @param pageable
   * @param draw
   * @return ResponseEntity
   */
  @GetMapping(name = "datatables", produces = Datatables.MEDIA_TYPE, value = "/dt")
  @ResponseBody
  public ResponseEntity<ConvertedDatatablesData<Pet>> datatables(@ModelAttribute Owner owner,
      DatatablesColumns columns, GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Pet> pets = petService.findByOwner(owner, search, pageable);
    long totalPetsCount = petService.countByOwner(owner);
    ConvertedDatatablesData<Pet> data =
        new ConvertedDatatablesData<Pet>(pets, totalPetsCount, draw, conversionService, columns);
    return ResponseEntity.ok(data);
  }

  public ResponseEntity<DatatablesData<Pet>> datatables(@ModelAttribute Owner owner,
      GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    throw new UnsupportedOperationException();
  }
}
