package com.disid.proofs.client.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import com.disid.proofs.client.domain.Operation;
import com.disid.proofs.client.domain.Person;
import com.disid.proofs.client.integration.MiddlewareIntegrationService;
import com.disid.proofs.client.service.api.PersonService;

import io.springlets.data.domain.GlobalSearch;

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
	 * This method obtains a list of People using the provided filter and the
	 * provided pagination delegating in a middleware operation. After that,
	 * synchronizes the client Database with the obtained People. Finally,
	 * returns all the existing people in our system.
	 * 
	 * @param globalSearch
	 * @param pageable
	 * @return Page
	 */
	@Transactional(readOnly = false)
	public Page<Person> findAll(GlobalSearch globalSearch, Pageable pageable) {
		// Communicate with the middleware to obtain a list of people
		List<Person> peopleFromMiddleware = integrationService.getAllPeople();

		// After that, save all the new obtained people into the local DB
		getPersonRepository().save(peopleFromMiddleware);

		// Finally, find all the registered people in the local DB
		return getPersonRepository().findAll(globalSearch, pageable);
	}

	/**
	 * TODO Auto-generated method documentation
	 * 
	 * @param person
	 * @param operations
	 * @return Person
	 */
	@Transactional
	public Person setOperations(Person person, Iterable<Long> operations) {
		List<Operation> items = getOperationService().findAll(operations);
		Set<Operation> currents = person.getOperations();
		Set<Operation> toRemove = new HashSet<Operation>();
		for (Iterator<Operation> iterator = currents.iterator(); iterator.hasNext();) {
			Operation nextOperation = iterator.next();
			if (items.contains(nextOperation)) {
				items.remove(nextOperation);
			} else {
				toRemove.add(nextOperation);
			}
		}
		person.removeFromOperations(toRemove);
		person.addToOperations(items);
		return getPersonRepository().save(person);
	}

}
