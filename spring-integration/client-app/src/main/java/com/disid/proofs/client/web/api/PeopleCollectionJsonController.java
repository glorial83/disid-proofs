package com.disid.proofs.client.web.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.disid.proofs.client.domain.Person;

/**
 * = PeopleCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Person.class, pathPrefix = "api", type = ControllerType.COLLECTION)
@RooJSON
public class PeopleCollectionJsonController {

	/**
	 * UnsupportedOperation
	 * 
	 * @param person
	 * @param result
	 * @return ResponseEntity
	 */
	@PostMapping(name = "create")
	public ResponseEntity<?> create(@Valid @RequestBody Person person, BindingResult result) {
		throw new UnsupportedOperationException("Unsupported Operation");
	}
}
