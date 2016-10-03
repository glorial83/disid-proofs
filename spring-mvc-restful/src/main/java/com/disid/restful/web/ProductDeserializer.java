/**
 * 
 */
package com.disid.restful.web;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;
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

/**
 * Deserializer which gets a {@link Product} id and returns a {@link Product} 
 * instance loaded through the {@link ProductService}.
 * The {@link ConversionService} is used to convert the {@link Product} id 
 * from text to {@link Long}.
 * @author Cèsar Ordiñana
 */
@Component
public class ProductDeserializer extends JsonDeserializer<Product> {

  private ProductService productService;
  private ConversionService conversionService;

  @Autowired
  public ProductDeserializer(ProductService productService, ConversionService conversionService) {
    this.productService = productService;
    this.conversionService = conversionService;
  }

  @Override
  public Product deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String idText = null;
    try {
      ObjectCodec codec = jp.getCodec();
      JsonNode tree = codec.readTree(jp);
      idText = tree.asText();
      Long id = conversionService.convert(idText, Long.class);
      return productService.findOne(id);
    } catch (Exception ex) {
      if (ex instanceof IOException) {
        throw (IOException) ex;
      }
      String msg =
          idText == null ? "Product deserialize error"
              : "Could not find Product with id: " + idText;
      throw new JsonMappingException(jp, msg, ex);
    }
  }

}
