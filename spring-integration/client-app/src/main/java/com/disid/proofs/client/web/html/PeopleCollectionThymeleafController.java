package com.disid.proofs.client.web.html;

import java.util.Collection;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disid.proofs.client.domain.Person;

/**
 * = PeopleCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController( entity = Person.class, type = ControllerType.COLLECTION )
@RooThymeleaf
public class PeopleCollectionThymeleafController
{

  /**
   * Unsupported Operation
   * 
   * @param person
   * @param result
   * @param model
   * @return ModelAndView
   */
  @PostMapping( name = "create" )
  public ModelAndView create( @Valid @ModelAttribute Person person, BindingResult result, Model model )
  {
    throw new UnsupportedOperationException(
        "Clients creation is not available. " + "Clients will be synchronized from the server side. " );
  }

  /**
   * Unsupported Operation
   * 
   * @param model
   * @return ModelAndView
   */
  @GetMapping( value = "/create-form", name = "createForm" )
  public ModelAndView createForm( Model model )
  {
    throw new UnsupportedOperationException(
        "Clients creation is not available. " + "Clients will be synchronized from the server side. " );
  }

  /**
   * Unsupported Operation
   * 
   * @param ids
   * @return ResponseEntity
   */
  @DeleteMapping( value = "/batch/{ids}", name = "deleteBatch" )
  @ResponseBody
  public ResponseEntity<?> deleteBatch( @PathVariable( "ids" ) Collection<Long> ids )
  {
    throw new UnsupportedOperationException(
        "Clients deletion is not available. " + "Clients will be synchronized from the server side. " );
  }

}
