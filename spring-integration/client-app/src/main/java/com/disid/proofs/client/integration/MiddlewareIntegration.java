package com.disid.proofs.client.integration;

import java.util.List;

import com.disid.proofs.client.domain.Person;

/**
 * = MiddelwareIntegration
 * 
 * Interface that defines the API of the Middleware integration service
 */
public interface MiddlewareIntegration {

	/**
	 * This operation should obtain all the people list delegating into the
	 * middleware
	 * 
	 * @return
	 */
	public List<Person> getAllPeople();

}
