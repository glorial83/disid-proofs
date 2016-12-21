package org.springframework.roo.petclinic.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;
import org.springframework.roo.petclinic.domain.Visit;
import org.springframework.roo.petclinic.service.api.VisitService;

/**
 * = VisitDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Visit.class)
public class VisitDeserializer extends JsonObjectDeserializer<Visit> {

    /**
     * TODO Auto-generated field documentation
     *
     */
    public VisitService visitService;

    /**
     * TODO Auto-generated field documentation
     *
     */
    public ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param visitService
     * @param conversionService
     */
    @Autowired
    public VisitDeserializer(VisitService visitService, ConversionService conversionService) {
        this.visitService = visitService;
        this.conversionService = conversionService;
    }
}
