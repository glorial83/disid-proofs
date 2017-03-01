package org.disid.proof.web;
import org.disid.proof.domain.Author;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooDetail;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = AuthorsItemBooksThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Author.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "books")
@RooThymeleaf
public class AuthorsItemBooksThymeleafController {
}
