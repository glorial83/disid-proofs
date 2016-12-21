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
import org.springframework.roo.entityformat.domain.Pet;
import org.springframework.roo.entityformat.domain.Visit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * = PetsItemVisitsThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "visits")
@RooThymeleaf
public class PetsItemVisitsThymeleafController {

  @Autowired
  private ConversionService conversionService;

  /**
   * TODO Auto-generated method documentation
   *
   * @param pet
   * @param search
   * @param pageable
   * @param draw
   * @return ResponseEntity
   */
  @GetMapping(name = "datatables", produces = Datatables.MEDIA_TYPE, value = "/dt")
  @ResponseBody
  public ResponseEntity<ConvertedDatatablesData<Visit>> datatables(@ModelAttribute Pet pet,
      DatatablesColumns columns, GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Visit> visits = visitService.findByPet(pet, search, pageable);
    long totalVisitsCount = visitService.countByPet(pet);
    ConvertedDatatablesData<Visit> data = new ConvertedDatatablesData<Visit>(visits,
        totalVisitsCount, draw, conversionService, columns);
    return ResponseEntity.ok(data);
  }

  public ResponseEntity<DatatablesData<Visit>> datatables(@ModelAttribute Pet pet,
      GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    throw new UnsupportedOperationException();
  }
}
