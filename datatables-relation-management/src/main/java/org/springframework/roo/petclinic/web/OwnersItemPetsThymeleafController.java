package org.springframework.roo.petclinic.web;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooDetail;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * = OwnersItemPetsThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "pets", views = { "list", "show", "findByCityLike" })
@RooThymeleaf
public class OwnersItemPetsThymeleafController {
  
  @DeleteMapping(name = "removePets")
  @ResponseBody
  public ResponseEntity<?> removePets(@ModelAttribute Owner owner, @RequestParam("petsIds") List<Long> petsToRemove) {
      getOwnerService().removeFromPets(owner, petsToRemove);
      return ResponseEntity.ok().build();
  }
  
  
}
