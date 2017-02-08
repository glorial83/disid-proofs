package org.springframework.roo.relatedreadonly.web;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.finder.RooSearch;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.relatedreadonly.domain.Owner;

/**
 * = OwnersSearchThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.SEARCH)
@RooSearch(finders = { "findByFirstNameLike" })
@RooThymeleaf
public class OwnersSearchThymeleafController {
}
