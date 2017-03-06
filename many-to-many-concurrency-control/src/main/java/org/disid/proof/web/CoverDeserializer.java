package org.disid.proof.web;
import org.disid.proof.domain.Cover;
import org.disid.proof.service.api.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = CoverDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Cover.class)
public class CoverDeserializer extends JsonObjectDeserializer<Cover> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private CoverService coverService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param coverService
     * @param conversionService
     */
    @Autowired
    public CoverDeserializer(@Lazy CoverService coverService, ConversionService conversionService) {
        this.coverService = coverService;
        this.conversionService = conversionService;
    }
}
