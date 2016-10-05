/**
 * 
 */
package com.disid.restful.web;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.core.convert.ConversionService;

import java.io.IOException;

/**
 * Deserializer which gets a {@link Product} id and returns a {@link Product} 
 * instance loaded through the {@link ProductService}.
 * The {@link ConversionService} is used to convert the {@link Product} id 
 * from text to {@link Long}.
 * @author Cèsar Ordiñana
 */
@JsonComponent
public class ProductDeserializer extends JsonObjectDeserializer<Product> {

  private ProductService productService;
  private ConversionService conversionService;

  @Autowired
  public ProductDeserializer(ProductService productService, ConversionService conversionService) {
    this.productService = productService;
    this.conversionService = conversionService;
  }

  @Override
  protected Product deserializeObject(JsonParser jsonParser, DeserializationContext context,
      ObjectCodec codec, JsonNode tree) throws IOException {
    String idText = tree.asText();
    Long id = conversionService.convert(idText, Long.class);
    Product product = productService.findOne(id);
    if (product == null) {
      throw new NotFoundException("Product not found");
    }
    return product;
  }

}
