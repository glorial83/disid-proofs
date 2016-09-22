package com.springsource.petclinic.format;
import com.springsource.petclinic.domain.Pet;
import com.springsource.petclinic.service.api.PetService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Pet.class, service = PetService.class)
public class PetFormatter {
}
