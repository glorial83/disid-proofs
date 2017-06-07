package com.disid.proofs.server.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * = MiddlewareIntegrationImpl
 * 
 * Implementation of the MiddlewareIntegration interface
 */
@Service
@Transactional(readOnly = true)
public class MiddlewareIntegrationServiceImpl implements MiddlewareIntegrationService {

	/**
	 * The URL where the middleware is published without the port and without
	 * the context
	 */
	@Value("${middleware.url}")
	private String middlewareUrl;

	/**
	 * The PORT where the middleware is published
	 */
	@Value("${middleware.port}")
	private String middlewarePort;

	/**
	 * The context where the middleware is published
	 */
	@Value("${middleware.context}")
	private String middlewareContext;

	/**
	 * This implementation uses a RestTemplate to connect to the middleware. The
	 * middleware will be the responsible to notify to all the necessary
	 * applications that some person has been removed.
	 * 
	 * @param id
	 */
	@Override
	public void notifyPersonRemoved(Long id) {
		// Use a RestTemplate
		RestTemplate template = new RestTemplate();
		// Specify the uri where the service is located
		// Change this to use properties
		String uri = "http://".concat(middlewareUrl).concat(":").concat(middlewarePort).concat("/")
				.concat(middlewareContext).concat("/people/").concat(id.toString());
		// Execute the request
		template.exchange(uri, HttpMethod.DELETE, null, Void.class);
	}

}
