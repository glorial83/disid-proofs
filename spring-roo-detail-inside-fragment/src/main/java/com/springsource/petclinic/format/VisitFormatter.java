package com.springsource.petclinic.format;
import com.springsource.petclinic.domain.Visit;
import com.springsource.petclinic.service.api.VisitService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Visit.class, service = VisitService.class)
public class VisitFormatter {
}
