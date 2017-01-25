package org.springframework.roo.clinictests.dod;

import org.springframework.roo.clinictests.domain.Pet;
import org.springframework.roo.clinictests.domain.reference.PetType;

import java.util.Calendar;

/**
 * Factory of {@link Pet} transient entities for testing purposes. 
 * 
 * Data is generated using a simple criteria based on a given index and depending on the type 
 * of the property:
 * * String: its the property name with an index suffix.
 * * Date types: the current date.
 * * Boolean: true value.
 * * Numbers: the index value.
 * * Enum: the first enum value.
 *
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
public class PetFactory {

  /**
   * Creates a new generated but not persisted {@link Pet} with the given index. 
   * 
   * @param index position of the Pet
   * @return a new transient Pet
   */
  public Pet create(int index) {
    Pet obj = new Pet();
    setCreatedBy(obj, index);
    setCreatedDate(obj, index);
    setModifiedBy(obj, index);
    setModifiedDate(obj, index);
    setName(obj, index);
    setSendReminders(obj, index);
    setType(obj, index);
    setWeight(obj, index);
    return obj;
  }

  private void setCreatedBy(Pet obj, int index) {
    String createdBy = "createdBy_" + index;
    obj.setCreatedBy(createdBy);
  }

  private void setCreatedDate(Pet obj, int index) {
    Calendar createdDate = Calendar.getInstance();
    obj.setCreatedDate(createdDate);
  }

  private void setModifiedBy(Pet obj, int index) {
    String modifiedBy = "modifiedBy_" + index;
    obj.setModifiedBy(modifiedBy);
  }

  private void setModifiedDate(Pet obj, int index) {
    Calendar modifiedDate = Calendar.getInstance();
    obj.setModifiedDate(modifiedDate);
  }

  private void setName(Pet obj, int index) {
    String name = "name_" + index;
    obj.setName(name);
  }

  private void setSendReminders(Pet obj, int index) {
    Boolean sendReminders = true;
    obj.setSendReminders(sendReminders);
  }

  private void setType(Pet obj, int index) {
    PetType type = PetType.class.getEnumConstants()[0];
    obj.setType(type);
  }

  private void setWeight(Pet obj, int index) {
    Float weight = new Integer(index).floatValue();
    obj.setWeight(weight);
  }

}
