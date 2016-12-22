package org.springframework.roo.multiselect.web;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.multiselect.domain.Pet;

/**
 * = PetsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.ITEM)
@RooJSON
public class PetsItemJsonController {
}
