package com.disid.proofs.server.web.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.web.bind.annotation.GetMapping;

import com.disid.proofs.server.domain.Person;

import io.springlets.data.domain.GlobalSearch;

/**
 * = PeopleCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController( entity = Person.class, pathPrefix = "api", type = ControllerType.COLLECTION )
@RooJSON
public class PeopleCollectionJsonController
{

  /**
   * List operation that returns all the existing persons
   * 
   * @param globalSearch
   * @param pageable
   * @return ResponseEntity
   */
  @GetMapping( name = "list" )
  public ResponseEntity<List<Person>> list( GlobalSearch globalSearch, Pageable pageable )
  {

    List<Person> people = getPersonService().findAll();
    return ResponseEntity.ok( people );
  }

}
