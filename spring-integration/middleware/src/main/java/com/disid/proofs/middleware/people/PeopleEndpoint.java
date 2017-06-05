package com.disid.proofs.middleware.people;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

/**
 * = PeopleEndpoint
 * 
 * Necessary to use the ServiceActivator
 */
@Component(value = "peopleEndpoint")
public class PeopleEndpoint {

	@ServiceActivator
	public String handleRequest() throws Exception {
		return "";
	}

}
