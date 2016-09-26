/**
 * 
 */
package com.disid.restful.web;

import com.disid.restful.model.Product;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Cèsar Ordiñana
 */
@Component
public class ProductDeserializer extends JsonDeserializer<Product> {

  private ProductFormatter formatter;

  @Autowired
  public ProductDeserializer(ProductFormatter formatter) {
    this.formatter = formatter;
  }

  @Override
  public Product deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String id = null;
    try {
      ObjectCodec codec = jp.getCodec();
      JsonNode tree = codec.readTree(jp);
      id = tree.asText();
      return formatter.parse(id, null);
    } catch (Exception ex) {
      if (ex instanceof IOException) {
        throw (IOException) ex;
      }
      String msg =
          id == null ? "Product deserialize error" : "Could not find Product with id: " + id;
      throw new JsonMappingException(msg, ex);
    }
  }

}
