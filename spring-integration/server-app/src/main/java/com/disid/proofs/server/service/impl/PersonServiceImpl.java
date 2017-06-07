package com.disid.proofs.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import com.disid.proofs.server.domain.Person;
import com.disid.proofs.server.integration.MiddlewareIntegrationService;
import com.disid.proofs.server.service.api.PersonService;

/**
 * = PersonServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PersonService.class)
public class PersonServiceImpl implements PersonService {

	@Autowired
	private MiddlewareIntegrationService integrationService;

	/**
	 * This operation deletes an existing person. After that, notifies to the
	 * Middleware about the deletion and the middleware will be the responsible
	 * to alert to all the necessary applications.
	 * 
	 * @param person
	 */
	@Transactional
	public void delete(Person person) {
		// First of all, delete the current person
		getPersonRepository().delete(person);

		// Notify that some person has been removed
		integrationService.notifyPersonRemoved(person.getId());
	}

}
