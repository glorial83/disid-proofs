package org.springframework.roo.entityformat.config;
import org.springframework.roo.addon.ws.annotations.RooWsEndpoints;
import org.springframework.roo.entityformat.ws.endpoint.OwnerWebServiceEndpoint;
import org.springframework.roo.entityformat.ws.endpoint.PetWebServiceEndpoint;

/**
 * = WsEndpointsConfiguration
 *
 * TODO Auto-generated class documentation
 *
 */
@RooWsEndpoints(endpoints = { OwnerWebServiceEndpoint.class, PetWebServiceEndpoint.class })
public class WsEndpointsConfiguration {
}
