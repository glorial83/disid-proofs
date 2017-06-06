package com.disid.proofs.middleware.tools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.disid.proofs.middleware.tools.domain.ToolFromClient;
import com.disid.proofs.middleware.tools.domain.ToolFromServer;

/**
 * = ToolsResponseServiceActivator
 * 
 * Class that will convert the obtained Tool from the server side to the Tool
 * instance that matches with the client side.
 */
@Component("toolsResponseServiceActivator")
public class ToolsResponseServiceActivator {

	/**
	 * This operation receives a list of tools from the server side and returns
	 * a list of tools that matches with the tools object of the client side
	 * 
	 * @param toolsFromServer
	 * @return
	 */
	@ServiceActivator
	public List<ToolFromClient> handleTools(final List<ToolFromServer> toolsFromServer) {
		// Create the new list
		List<ToolFromClient> toolsFromClient = new ArrayList<ToolFromClient>();
		// Iterate over all the obtainer people from server and create a new
		// instance
		// using the specific constructor.
		for (ToolFromServer toolFromServer : toolsFromServer) {
			toolsFromClient.add(new ToolFromClient(toolFromServer));
		}
		return toolsFromClient;
	}

}
