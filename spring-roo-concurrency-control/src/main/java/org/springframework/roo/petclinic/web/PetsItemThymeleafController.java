package org.springframework.roo.petclinic.web;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Pet;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

/**
 * = PetsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Pet.class, type = ControllerType.ITEM)
@RooThymeleaf
public class PetsItemThymeleafController {
	
    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Pet pet, @RequestParam("version") Integer version, 
    		@RequestParam( value = "concurrency", required = false, defaultValue = "") String concurrencyControl, BindingResult result, Model model) {

    	// Check if provided form contain errors
    	if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView("pets/edit");
        }
        
        // Concurrency control
        Pet existingPet = getPetService().findOne(pet.getId());
        if(pet.getVersion() != existingPet.getVersion() && StringUtils.isEmpty(concurrencyControl)){
        	populateForm(model);
        	model.addAttribute("concurrency", true);
        	return new ModelAndView("pets/edit");
        } else if(pet.getVersion() != existingPet.getVersion() && "discard".equals(concurrencyControl)){
        	populateForm(model);
        	model.addAttribute("pet", existingPet);
        	model.addAttribute("concurrency", false);
        	return new ModelAndView("pets/edit"); 
        } else if(pet.getVersion() != existingPet.getVersion() && "apply".equals(concurrencyControl)){
        	// Update the version field to be able to override the existing
        	// values
        	pet.setVersion(existingPet.getVersion());
        }
        
        Pet savedPet = getPetService().save(pet);
        UriComponents showURI = getItemLink().to(PetsItemThymeleafLinkFactory.SHOW).with("pet", savedPet.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }
	
}
