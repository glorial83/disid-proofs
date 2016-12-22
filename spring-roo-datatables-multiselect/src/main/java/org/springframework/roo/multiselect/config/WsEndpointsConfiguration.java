package org.springframework.roo.multiselect.config;
import org.springframework.roo.addon.ws.annotations.RooWsEndpoints;
import org.springframework.roo.multiselect.ws.endpoint.OwnerWebServiceEndpoint;
import org.springframework.roo.multiselect.ws.endpoint.PetWebServiceEndpoint;

/**
 * = WsEndpointsConfiguration
 *
 * TODO Auto-generated class documentation
 *
 */
@RooWsEndpoints(endpoints = { OwnerWebServiceEndpoint.class, PetWebServiceEndpoint.class })
public class WsEndpointsConfiguration {
}
