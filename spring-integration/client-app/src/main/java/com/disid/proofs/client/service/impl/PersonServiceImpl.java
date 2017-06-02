package com.disid.proofs.client.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.web.client.RestTemplate;

import com.disid.proofs.client.domain.Person;
import com.disid.proofs.client.service.api.PersonService;
import com.lowagie.text.List;

import io.springlets.data.domain.GlobalSearch;

/**
 * = PersonServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl( service = PersonService.class )
public class PersonServiceImpl implements PersonService
{

  /**
   * This method obtains a list of People using the provided filter and
   * the provided pagination delegating in a middleware operation. After that,
   * synchronizes the client Database with the obtained People. Finally, returns
   * all the existing people in our system.
   * 
   * @param globalSearch
   * @param pageable
   * @return Page
   */
  @Cacheable( "peopleSync" )
  public Page<Person> findAll( GlobalSearch globalSearch, Pageable pageable )
  {
    // TODO: Communicate with the middleware to obtain a list of Persons
    RestTemplate template = new RestTemplate();
    String uri = "http://localhost:8082/middleware/people";
    ResponseEntity<?> httpResponse = template.exchange( uri, HttpMethod.GET, null, List.class );
    return getPersonRepository().findAll( globalSearch, pageable );
  }

}
