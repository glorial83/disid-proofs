/**
 * 
 */
package com.disid.restful.web;

import com.disid.restful.model.Customer;
import com.disid.restful.service.api.CustomerService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Cèsar Ordiñana
 */
@Component
public class CustomerDeserializer extends JsonDeserializer<Customer> {

  private CustomerFormatter formatter;

  @Autowired
  public CustomerDeserializer(CustomerService customerService,
      ConversionService conversionService) {
    this.formatter = new CustomerFormatter(customerService, conversionService);
  }

  @Override
  public Customer deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    try {
      ObjectCodec codec = jp.getCodec();
      JsonNode tree = codec.readTree(jp);
      return deserializeObject(jp, ctxt, codec, tree);
    } catch (Exception ex) {
      if (ex instanceof IOException) {
        throw (IOException) ex;
      }
      throw new JsonMappingException("Object deserialize error", ex);
    }
  }

  private Customer deserializeObject(JsonParser jp, DeserializationContext ctxt, ObjectCodec codec,
      JsonNode tree) throws ParseException {
    String id = tree.asText();
    return formatter.parse(id, null);
  }

}
