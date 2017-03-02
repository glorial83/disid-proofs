package org.disid.proof.web;
import org.disid.proof.domain.Editorial;
import org.disid.proof.service.api.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = EditorialDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Editorial.class)
public class EditorialDeserializer extends JsonObjectDeserializer<Editorial> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private EditorialService editorialService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param editorialService
     * @param conversionService
     */
    @Autowired
    public EditorialDeserializer(@Lazy EditorialService editorialService, ConversionService conversionService) {
        this.editorialService = editorialService;
        this.conversionService = conversionService;
    }
}
