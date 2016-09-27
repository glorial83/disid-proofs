package com.springsource.petclinic.format;
import com.springsource.petclinic.domain.Vet;
import com.springsource.petclinic.service.api.VetService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Vet.class, service = VetService.class)
public class VetFormatter {
}
