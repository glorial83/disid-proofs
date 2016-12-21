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
import org.springframework.roo.entityformat.domain.Vet;
import org.springframework.roo.entityformat.domain.Visit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * = VetsItemVisitsThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Vet.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "visits")
@RooThymeleaf
public class VetsItemVisitsThymeleafController {

  @Autowired
  private ConversionService conversionService;

  /**
   * TODO Auto-generated method documentation
   *
   * @param vet
   * @param search
   * @param pageable
   * @param draw
   * @return ResponseEntity
   */
  @GetMapping(name = "datatables", produces = Datatables.MEDIA_TYPE, value = "/dt")
  @ResponseBody
  public ResponseEntity<ConvertedDatatablesData<Visit>> datatables(@ModelAttribute Vet vet,
      DatatablesColumns columns, GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Visit> visits = visitService.findByVet(vet, search, pageable);
    long totalVisitsCount = visitService.countByVet(vet);
    ConvertedDatatablesData<Visit> data = new ConvertedDatatablesData<Visit>(visits,
        totalVisitsCount, draw, conversionService, columns);
    return ResponseEntity.ok(data);
  }

  public ResponseEntity<DatatablesData<Visit>> datatables(@ModelAttribute Vet vet,
      GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    throw new UnsupportedOperationException();
  }
}
