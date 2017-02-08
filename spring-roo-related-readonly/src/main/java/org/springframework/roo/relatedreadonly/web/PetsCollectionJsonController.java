package org.springframework.roo.relatedreadonly.web;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.relatedreadonly.domain.Pet;

/**
 * = PetsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.COLLECTION)
@RooJSON
public class PetsCollectionJsonController {
}
