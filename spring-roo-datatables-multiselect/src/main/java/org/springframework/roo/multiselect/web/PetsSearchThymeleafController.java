package org.springframework.roo.multiselect.web;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.finder.RooSearch;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.multiselect.domain.Pet;

/**
 * = PetsSearchThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.SEARCH)
@RooSearch(finders = { "findByNameAndWeight" })
@RooThymeleaf
public class PetsSearchThymeleafController {
}
