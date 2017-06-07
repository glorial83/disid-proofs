package com.disid.proofs.client.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.disid.proofs.client.domain.Person;

/**
 * = PeopleItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Person.class, pathPrefix = "api", type = ControllerType.ITEM)
@RooJSON
public class PeopleItemJsonController {

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * TODO Auto-generated method documentation
	 * 
	 * @param id
	 * @return Person
	 */
	@ModelAttribute
	public Person getPerson(@PathVariable("person") Long id) {
		return getPersonService().findOne(id);
	}

	/**
	 * UnsupportedOperation
	 * 
	 * @param storedPerson
	 * @param person
	 * @param result
	 * @return ResponseEntity
	 */
	@PutMapping(name = "update")
	public ResponseEntity<?> update(@ModelAttribute Person storedPerson, @Valid @RequestBody Person person,
			BindingResult result) {
		throw new UnsupportedOperationException("Unsupported Operation");
	}

	/**
	 * Method that deletes a provided person and after that, notifies to the
	 * view side to show the notifications about deletion
	 * 
	 * @param person
	 * @return ResponseEntity
	 */
	@DeleteMapping(name = "delete")
	public ResponseEntity<?> delete(@ModelAttribute Person person) {
		if (person != null) {
			// Delete the provided person
			getPersonService().delete(person);
			// Notify to the view side
			this.template.convertAndSend("/topic/messages", person.getName());
		}

		return ResponseEntity.ok().build();
	}
}
