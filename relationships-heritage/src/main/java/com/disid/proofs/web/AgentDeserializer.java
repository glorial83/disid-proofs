package com.disid.proofs.web;
import com.disid.proofs.domain.Agent;
import com.disid.proofs.service.api.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = AgentDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Agent.class)
public class AgentDeserializer extends JsonObjectDeserializer<Agent> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private AgentService agentService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param agentService
     * @param conversionService
     */
    @Autowired
    public AgentDeserializer(@Lazy AgentService agentService, ConversionService conversionService) {
        this.agentService = agentService;
        this.conversionService = conversionService;
    }
}
