package com.disid.proofs.client.web.api;
import com.disid.proofs.client.domain.Person;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;

/**
 * = PeopleCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Person.class, pathPrefix = "api", type = ControllerType.COLLECTION)
@RooJSON
public class PeopleCollectionJsonController {
}
