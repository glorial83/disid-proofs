package com.disid.proofs.web;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disid.proofs.domain.CallOption;
import com.disid.proofs.domain.Menu;
import com.disid.proofs.service.api.CallOptionService;
import com.disid.proofs.service.api.MenuService;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.select2.Select2DataSupport;
import io.springlets.data.web.select2.Select2DataWithConversion;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;

/**
 * = MenusCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Menu.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class MenusCollectionThymeleafController {
  
  private CallOptionService callOptionService;
  
  /**
   * TODO Auto-generated constructor documentation
   * 
   * @param menuService
   * @param conversionService
   * @param messageSource
   * @param linkBuilder
   */
  @Autowired
  public MenusCollectionThymeleafController(MenuService menuService, CallOptionService callOptionService, 
      ConversionService conversionService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
      setMenuService(menuService);
      setConversionService(conversionService);
      setMessageSource(messageSource);
      setItemLink(linkBuilder.of(MenusItemThymeleafController.class));
      this.callOptionService = callOptionService;
  }
  
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
  public ResponseEntity<Select2DataSupport<CallOption>> select2(GlobalSearch search, Pageable pageable, Locale locale) {
      Page<CallOption> options = callOptionService.findAll(search, pageable);
      String idExpression = "#{id}";
      Select2DataSupport<CallOption> select2Data = new Select2DataWithConversion<CallOption>(options, idExpression, getConversionService());
      return ResponseEntity.ok(select2Data);
  }
}
