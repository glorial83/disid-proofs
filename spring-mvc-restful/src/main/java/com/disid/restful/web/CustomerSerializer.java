/**
 * 
 */
package com.disid.restful.web;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Cèsar Ordiñana
 */
@Component
public class CustomerSerializer extends JsonSerializer<Customer> {

  private CustomerFormatter formatter;

  @Autowired
  public CustomerSerializer(CustomerService customerService, ConversionService conversionService) {
    this.formatter = new CustomerFormatter(customerService, conversionService);
  }

  @Override
  public void serialize(Customer customer, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {

    gen.writeString(formatter.print(customer, null));
  }

}
