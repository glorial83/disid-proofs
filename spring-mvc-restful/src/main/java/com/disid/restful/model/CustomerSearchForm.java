package com.disid.restful.model;

public class CustomerSearchForm {

  private final String firstName;

  private final String lastName;

  public CustomerSearchForm(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

}
