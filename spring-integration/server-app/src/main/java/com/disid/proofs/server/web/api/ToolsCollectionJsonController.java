package com.disid.proofs.server.web.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.web.bind.annotation.GetMapping;

import com.disid.proofs.server.domain.Tool;

import io.springlets.data.domain.GlobalSearch;

/**
 * = ToolsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Tool.class, pathPrefix = "api", type = ControllerType.COLLECTION)
@RooJSON
public class ToolsCollectionJsonController {

	/**
	 * Lists all the registered tools
	 * 
	 * @param globalSearch
	 * @param pageable
	 * @return ResponseEntity
	 */
	@GetMapping(name = "list")
	public ResponseEntity<List<Tool>> list(GlobalSearch globalSearch, Pageable pageable) {
		List<Tool> tools = getToolService().findAll();
		return ResponseEntity.ok(tools);
	}
}
