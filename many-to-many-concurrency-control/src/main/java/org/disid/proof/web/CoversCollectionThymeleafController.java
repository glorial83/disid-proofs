package org.disid.proof.web;
import org.disid.proof.domain.Cover;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = CoversCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Cover.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class CoversCollectionThymeleafController {
}
