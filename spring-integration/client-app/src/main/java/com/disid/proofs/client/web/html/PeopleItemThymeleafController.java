package com.disid.proofs.client.web.html;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disid.proofs.client.domain.Person;

/**
 * = PeopleItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController( entity = Person.class, type = ControllerType.ITEM )
@RooThymeleaf
public class PeopleItemThymeleafController
{

  /**
   * Unsupported Operation
   * 
   * @param person
   * @param model
   * @return ModelAndView
   */
  @GetMapping( value = "/edit-form", name = "editForm" )
  public ModelAndView editForm( @ModelAttribute Person person, Model model )
  {
    throw new UnsupportedOperationException(
        "Clients edition is not available. " + "Clients will be synchronized from the server side. " );
  }

  /**
   * Unsupported Operation
   * 
   * @param person
   * @param version
   * @param concurrencyControl
   * @param result
   * @param model
   * @return ModelAndView
   */
  @PutMapping( name = "update" )
  public ModelAndView update( @Valid @ModelAttribute Person person, @RequestParam( "version" ) Integer version,
      @RequestParam( value = "concurrency", required = false, defaultValue = "" ) String concurrencyControl,
      BindingResult result, Model model )
  {
    throw new UnsupportedOperationException(
        "Clients edition is not available. " + "Clients will be synchronized from the server side. " );
  }

  /**
   * Unsupported Operation
   * 
   * @param person
   * @return ResponseEntity
   */
  @ResponseBody
  @DeleteMapping( name = "delete" )
  public ResponseEntity<?> delete( @ModelAttribute Person person )
  {
    throw new UnsupportedOperationException(
        "Clients deletion is not available. " + "Clients will be synchronized from the server side. " );
  }

}
