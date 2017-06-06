package com.disid.proofs.client.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.disid.proofs.client.domain.Person;
import com.disid.proofs.client.domain.Tool;

/**
 * = MiddlewareIntegrationImpl
 * 
 * Implementation of the MiddlewareIntegration interface
 */
@Service
@Transactional(readOnly = true)
public class MiddlewareIntegrationServiceImpl implements MiddlewareIntegrationService {

	/**
	 * The URL where the middleware is published without the port and without
	 * the context
	 */
	@Value("${middleware.url}")
	private String middlewareUrl;

	/**
	 * The PORT where the middleware is published
	 */
	@Value("${middleware.port}")
	private String middlewarePort;

	/**
	 * The context where the middleware is published
	 */
	@Value("${middleware.context}")
	private String middlewareContext;

	/**
	 * This implementation uses a RestTemplate to connect to the middleware. The
	 * middleware will be the responsible to obtain the list of people from all
	 * the necessary servicies. The obtained list of people from the middleware
	 * is a valid List of people in our client format.
	 * 
	 * @return A List<Person> that contains all the people registered in the
	 *         server or servers side.
	 */
	@Override
	public List<Person> getAllPeople() {
		// Use a RestTemplate
		RestTemplate template = new RestTemplate();
		// Specify the uri where the service is located
		// Change this to use properties
		String uri = "http://".concat(middlewareUrl).concat(":").concat(middlewarePort).concat("/")
				.concat(middlewareContext).concat("/people");
		// Execute the request
		ResponseEntity<Person[]> httpResponse = template.exchange(uri, HttpMethod.GET, null, Person[].class);
		// Return the obtained elements from the http response
		return Arrays.asList(httpResponse.getBody());
	}

	/**
	 * This implementation uses a RestTemplate to connect to the middleware. The
	 * middleware will be the responsible to obtain the list of tools from all
	 * the necessary servicies. The obtained list of tools from the middleware
	 * is a valid List of Tool in our client format.
	 * 
	 * @return A List<Tool> that contains all the tools registered in the server
	 *         or servers side.
	 */
	@Override
	public List<Tool> getAllTools() {
		// Use a RestTemplate
		RestTemplate template = new RestTemplate();
		// Specify the uri where the service is located
		// Change this to use properties
		String uri = "http://".concat(middlewareUrl).concat(":").concat(middlewarePort).concat("/")
				.concat(middlewareContext).concat("/tools");
		// Execute the request
		ResponseEntity<Tool[]> httpResponse = template.exchange(uri, HttpMethod.GET, null, Tool[].class);
		// Return the obtained elements from the http response
		return Arrays.asList(httpResponse.getBody());
	}

}
