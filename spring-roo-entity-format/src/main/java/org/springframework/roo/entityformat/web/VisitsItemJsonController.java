package org.springframework.roo.entityformat.web;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.entityformat.domain.Visit;

/**
 * = VisitsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Visit.class, type = ControllerType.ITEM)
@RooJSON
public class VisitsItemJsonController {
}
