package com.disid.proofs.middleware.people;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.disid.proofs.middleware.people.domain.PersonFromClient;
import com.disid.proofs.middleware.people.domain.PersonFromServer;

/**
 * = PeopleResponseServiceActivator
 * 
 * Class that will convert the obtained People from the server side to the
 * People instance that matches with the client side.
 */
@Component("peopleResponseServiceActivator")
public class PeopleResponseServiceActivator {

	/**
	 * This operation receives a list of people from the server side and returns
	 * a list of people that matches with the person object of the client side
	 * 
	 * @param peopleFromServer
	 * @return
	 */
	@ServiceActivator
	public List<PersonFromClient> handlePeople(final List<PersonFromServer> peopleFromServer) {
		// Create the new list
		List<PersonFromClient> peopleFromClient = new ArrayList<PersonFromClient>();
		// Iterate over all the obtainer people from server and create a new
		// instance
		// using the specific constructor.
		for (PersonFromServer personFromServer : peopleFromServer) {
			peopleFromClient.add(new PersonFromClient(personFromServer));
		}
		return peopleFromClient;
	}

}
