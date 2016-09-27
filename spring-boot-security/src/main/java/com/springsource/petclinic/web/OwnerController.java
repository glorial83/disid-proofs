package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Owner;
import com.springsource.petclinic.service.api.OwnerService;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

@RooController(path = "/owners", entity = Owner.class, service = OwnerService.class)
@RooThymeleaf
public class OwnerController {
}
