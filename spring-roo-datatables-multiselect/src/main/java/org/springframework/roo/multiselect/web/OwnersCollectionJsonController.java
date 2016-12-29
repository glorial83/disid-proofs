package org.springframework.roo.multiselect.web;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import org.springframework.roo.multiselect.domain.Owner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * = OwnersCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.COLLECTION)
@RooJSON
public class OwnersCollectionJsonController {


  /**
   * TODO Auto-generated method documentation
   * 
   * @param ids
   * @return ResponseEntity
   */
  @DeleteMapping(value = "/batchx/{ids}", name = "deleteBatch")
  public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {

    ownerService.delete(ids);

    return ResponseEntity.ok().build();
  }

}
