package com.springsource.petclinic.format;
import com.springsource.petclinic.domain.Owner;
import com.springsource.petclinic.service.api.OwnerService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Owner.class, service = OwnerService.class)
public class OwnerFormatter {
}
