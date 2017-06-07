package com.disid.proofs.server.integration;

/**
 * = MiddelwareIntegration
 * 
 * Interface that defines the API of the Middleware integration service
 */
public interface MiddlewareIntegrationService {

	/**
	 * This operation should notify that an existing person has been removed.
	 * 
	 * @param id
	 */
	public void notifyPersonRemoved(Long id);

}
