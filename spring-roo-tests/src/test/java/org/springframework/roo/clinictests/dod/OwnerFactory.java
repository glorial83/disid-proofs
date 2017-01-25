package org.springframework.roo.clinictests.dod;

import org.springframework.roo.clinictests.domain.Owner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Factory of {@link Owner} transient entities for testing purposes.
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
public class OwnerFactory {

  /**
   * Creates a new {@link Owner} with the given index. 
   * 
   * @param index position of the Owner
   * @return a new transient Owner
   */
  public Owner create(int index) {
    Owner obj = new Owner();
    setAddress(obj, index);
    setBirthDay(obj, index);
    setCity(obj, index);
    setCreatedBy(obj, index);
    setCreatedDate(obj, index);
    setEmail(obj, index);
    setFirstName(obj, index);
    setHomePage(obj, index);
    setLastName(obj, index);
    setModifiedBy(obj, index);
    setModifiedDate(obj, index);
    setTelephone(obj, index);
    return obj;
  }

  private void setAddress(Owner obj, int index) {
    String address = "address_" + index;
    if (address.length() > 50) {
      address = address.substring(0, 50);
    }
    obj.setAddress(address);
  }

  private void setBirthDay(Owner obj, int index) {
    Date birthDay = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue())
            .getTime();
    obj.setBirthDay(birthDay);
  }

  private void setCity(Owner obj, int index) {
    String city = "city_" + index;
    if (city.length() > 30) {
      city = city.substring(0, 30);
    }
    obj.setCity(city);
  }

  private void setCreatedBy(Owner obj, int index) {
    String createdBy = "createdBy_" + index;
    obj.setCreatedBy(createdBy);
  }

  private void setCreatedDate(Owner obj, int index) {
    Calendar createdDate = Calendar.getInstance();
    obj.setCreatedDate(createdDate);
  }

  private void setEmail(Owner obj, int index) {
    String email = "foo" + index + "@bar.com";
    if (email.length() > 30) {
      email = email.substring(0, 30);
    }
    obj.setEmail(email);
  }

  private void setFirstName(Owner obj, int index) {
    String firstName = "firstName_" + index;
    if (firstName.length() > 30) {
      firstName = firstName.substring(0, 30);
    }
    obj.setFirstName(firstName);
  }

  private void setHomePage(Owner obj, int index) {
    String homePage = "homePage_" + index;
    if (homePage.length() > 30) {
      homePage = homePage.substring(0, 30);
    }
    obj.setHomePage(homePage);
  }

  private void setLastName(Owner obj, int index) {
    String lastName = "lastName_" + index;
    if (lastName.length() > 30) {
      lastName = lastName.substring(0, 30);
    }
    obj.setLastName(lastName);
  }

  private void setModifiedBy(Owner obj, int index) {
    String modifiedBy = "modifiedBy_" + index;
    obj.setModifiedBy(modifiedBy);
  }

  private void setModifiedDate(Owner obj, int index) {
    Calendar modifiedDate = Calendar.getInstance();
    obj.setModifiedDate(modifiedDate);
  }

  private void setTelephone(Owner obj, int index) {
    String telephone = "telephone_" + index;
    obj.setTelephone(telephone);
  }

}
