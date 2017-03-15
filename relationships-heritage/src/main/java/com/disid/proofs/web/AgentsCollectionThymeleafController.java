package com.disid.proofs.web;
import com.disid.proofs.domain.Agent;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = AgentsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Agent.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class AgentsCollectionThymeleafController {
}
