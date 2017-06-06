package com.disid.proofs.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import com.disid.proofs.client.domain.Tool;
import com.disid.proofs.client.integration.MiddlewareIntegrationService;
import com.disid.proofs.client.service.api.ToolService;

import io.springlets.data.domain.GlobalSearch;

/**
 * = ToolServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = ToolService.class)
public class ToolServiceImpl implements ToolService {

	@Autowired
	private MiddlewareIntegrationService integrationService;

	/**
	 * This method obtains a list of Tools using the provided filter and the
	 * provided pagination delegating in a middleware operation. After that,
	 * synchronizes the client Database with the obtained Tools. Finally,
	 * returns all the existing tools in our system.
	 * 
	 * @param globalSearch
	 * @param pageable
	 * @return Page
	 */
	@Transactional(readOnly = false)
	public Page<Tool> findAll(GlobalSearch globalSearch, Pageable pageable) {

		// Communicate with the middleware to obtain a list of tools
		List<Tool> toolsFromMiddleware = integrationService.getAllTools();

		// After that, save all the new obtained tools into the local DB
		getToolRepository().save(toolsFromMiddleware);
		return getToolRepository().findAll(globalSearch, pageable);
	}
}
