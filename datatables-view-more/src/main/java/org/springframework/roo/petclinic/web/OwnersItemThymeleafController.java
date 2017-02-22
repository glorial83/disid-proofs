package org.springframework.roo.petclinic.web;

import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Owner;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 * = OwnersItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.ITEM)
@RooThymeleaf
public class OwnersItemThymeleafController {


   /**
    * This method will return the inline section of the show.html view.
    */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Owner owner, Model model) {
        return new ModelAndView("owners/showInline :: inline-content");
    }


}
