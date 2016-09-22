package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Vet;
import com.springsource.petclinic.service.api.VetService;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

@RooController(path = "/vets", entity = Vet.class, service = VetService.class)
@RooThymeleaf
public class VetController {
}
