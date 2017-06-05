package com.disid.proofs.client.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.disid.proofs.client.domain.Person;

/**
 * = MiddlewareIntegrationImpl
 * 
 * Implementation of the MiddlewareIntegration interface
 */
@Service
@Transactional(readOnly = true)
public class MiddlewareIntegrationImpl implements MiddlewareIntegration {

	/**
	 * This implementation uses a RestTemplate to connect to the middleware. The
	 * middleware will be the responsible to obtain the list of people from all
	 * the necessary servicies. The obtained list of people from the middleware
	 * is a valid List of Persons in our format.
	 * 
	 * @return A List<Person> that contains all the people registered in the
	 *         server or servers side.
	 */
	@Override
	public List<Person> getAllPeople() {
		// Use a RestTemplate
		RestTemplate template = new RestTemplate();
		// Specify the uri where the service is located
		// TODO: Change this to use properties
		String uri = "http://localhost:8082/middleware/people";
		// Execute the request
		ResponseEntity<Person[]> httpResponse = template.exchange(uri, HttpMethod.GET, null, Person[].class);
		// Return the obtained elements from the http response
		return Arrays.asList(httpResponse.getBody());
	}

}
