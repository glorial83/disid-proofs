package com.disid.proofs.web;
import com.disid.proofs.domain.Redirect;
import com.disid.proofs.service.api.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = RedirectDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Redirect.class)
public class RedirectDeserializer extends JsonObjectDeserializer<Redirect> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RedirectService redirectService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param redirectService
     * @param conversionService
     */
    @Autowired
    public RedirectDeserializer(@Lazy RedirectService redirectService, ConversionService conversionService) {
        this.redirectService = redirectService;
        this.conversionService = conversionService;
    }
}
