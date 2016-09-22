package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Visit;
import com.springsource.petclinic.service.api.VisitService;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

@RooController(path = "/visits", entity = Visit.class, service = VisitService.class)
@RooThymeleaf
public class VisitController {
}
