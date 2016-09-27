package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Pet;
import com.springsource.petclinic.service.api.PetService;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

@RooController(path = "/pets", entity = Pet.class, service = PetService.class)
@RooThymeleaf
public class PetController {
}
