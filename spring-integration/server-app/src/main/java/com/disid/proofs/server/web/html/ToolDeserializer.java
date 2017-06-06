package com.disid.proofs.server.web.html;
import com.disid.proofs.server.domain.Tool;
import com.disid.proofs.server.service.api.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = ToolDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Tool.class)
public class ToolDeserializer extends JsonObjectDeserializer<Tool> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ToolService toolService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param toolService
     * @param conversionService
     */
    @Autowired
    public ToolDeserializer(@Lazy ToolService toolService, ConversionService conversionService) {
        this.toolService = toolService;
        this.conversionService = conversionService;
    }
}
