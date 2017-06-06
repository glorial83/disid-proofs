package com.disid.proofs.server.web.api;
import com.disid.proofs.server.domain.Tool;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;

/**
 * = ToolsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Tool.class, pathPrefix = "api", type = ControllerType.ITEM)
@RooJSON
public class ToolsItemJsonController {
}
