package org.springframework.roo.entityformat.web;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.datatables.ConvertedDatatablesData;
import io.springlets.data.web.datatables.Datatables;
import io.springlets.data.web.datatables.DatatablesColumns;
import io.springlets.data.web.datatables.DatatablesData;
import io.springlets.data.web.datatables.DatatablesPageable;
import io.springlets.data.web.select2.Select2DataSupport;
import io.springlets.data.web.select2.Select2DataWithConversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.entityformat.domain.Vet;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * = VetsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Vet.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class VetsCollectionThymeleafController {

  @Autowired
  private ConversionService conversionService;

  /**
   * TODO Auto-generated method documentation
   *
   * @param search
   * @param pageable
   * @param locale
   * @return ResponseEntity
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, name = "select2", value = "/s2")
  @ResponseBody
  public ResponseEntity<Select2DataSupport<Vet>> select2(GlobalSearch search, Pageable pageable,
      Locale locale) {
    Page<Vet> Vets = vetService.findAll(search, pageable);
    String idExpression = "#{id}";
    Select2DataSupport<Vet> select2Data =
        new Select2DataWithConversion<Vet>(Vets, idExpression, conversionService);
    return ResponseEntity.ok(select2Data);
  }

  /**
   * TODO Auto-generated method documentation
   *
   * @param search
   * @param pageable
   * @param draw
   * @return ResponseEntity
   */
  @GetMapping(produces = Datatables.MEDIA_TYPE, name = "datatables", value = "/dt")
  @ResponseBody
  public ResponseEntity<ConvertedDatatablesData<Vet>> datatables(DatatablesColumns columns,
      GlobalSearch search, DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    Page<Vet> vets = vetService.findAll(search, pageable);
    long totalVetsCount = vets.getTotalElements();
    if (search != null && StringUtils.hasText(search.getText())) {
      totalVetsCount = vetService.count();
    }
    ConvertedDatatablesData<Vet> datatablesData =
        new ConvertedDatatablesData<Vet>(vets, totalVetsCount, draw, conversionService, columns);
    return ResponseEntity.ok(datatablesData);
  }

  public ResponseEntity<DatatablesData<Vet>> datatables(GlobalSearch search,
      DatatablesPageable pageable, @RequestParam("draw") Integer draw) {
    throw new UnsupportedOperationException();
  }
}
