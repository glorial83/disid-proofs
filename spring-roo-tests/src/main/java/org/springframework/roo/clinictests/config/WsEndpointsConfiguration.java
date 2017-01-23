package org.springframework.roo.clinictests.config;
import org.springframework.roo.addon.ws.annotations.RooWsEndpoints;
import org.springframework.roo.clinictests.ws.endpoint.OwnerWebServiceEndpoint;
import org.springframework.roo.clinictests.ws.endpoint.PetWebServiceEndpoint;

/**
 * = WsEndpointsConfiguration
 *
 * TODO Auto-generated class documentation
 *
 */
@RooWsEndpoints(endpoints = { OwnerWebServiceEndpoint.class, PetWebServiceEndpoint.class })
public class WsEndpointsConfiguration {
}
