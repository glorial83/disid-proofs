package com.disid.restful.web.product.html;

import com.disid.restful.model.Product;
import com.disid.restful.service.api.ProductService;

import io.springlets.web.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/products/{product}", produces = MediaType.TEXT_HTML_VALUE)
public class ProductsItemThymeleafController {

  public ProductService productService;
  private MessageSource messageSource;

  @Autowired
  public ProductsItemThymeleafController(ProductService productService,
      MessageSource messageSource) {
    this.productService = productService;
    this.messageSource = messageSource;
  }

  @ModelAttribute
  public Product getProduct(@PathVariable("product") Long id, Locale locale) {
    Product product = productService.findOne(id);
    if (product == null) {
      String message = messageSource.getMessage("error_productNotFound", null, locale);
      throw new NotFoundException(message);
    }
    return product;
  }

  @GetMapping("/edit-form")
  public String editForm(@ModelAttribute Product product, Model model) {
    return "products/edit";
  }

  @PutMapping
  public String update(@Valid @ModelAttribute Product product, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "products/edit";
    }
    Product savedProduct = productService.save(product);
    redirectAttrs.addAttribute("id", savedProduct.getId());
    return "redirect:/products/{id}";
  }

  @DeleteMapping
  public String delete(@ModelAttribute Product product, Model model) {
    productService.delete(product);
    return "redirect:/products";
  }

  @GetMapping
  public String show(@ModelAttribute Product product, Model model) {
    return "products/show";
  }

}
