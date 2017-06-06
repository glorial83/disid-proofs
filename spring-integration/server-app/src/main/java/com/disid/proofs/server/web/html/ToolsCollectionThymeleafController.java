package com.disid.proofs.server.web.html;
import com.disid.proofs.server.domain.Tool;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = ToolsCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Tool.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class ToolsCollectionThymeleafController {
}
