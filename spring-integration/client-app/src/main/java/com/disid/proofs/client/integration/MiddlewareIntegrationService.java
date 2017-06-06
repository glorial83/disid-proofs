package com.disid.proofs.client.integration;

import java.util.List;

import com.disid.proofs.client.domain.Person;
import com.disid.proofs.client.domain.Tool;

/**
 * = MiddelwareIntegration
 * 
 * Interface that defines the API of the Middleware integration service
 */
public interface MiddlewareIntegrationService {

	/**
	 * This operation should obtain all the people list delegating into the
	 * middleware
	 * 
	 * @return
	 */
	public List<Person> getAllPeople();

	/**
	 * This operation should obtain all the tools list delegating into the
	 * middleware
	 * 
	 * @return
	 */
	public List<Tool> getAllTools();

}
