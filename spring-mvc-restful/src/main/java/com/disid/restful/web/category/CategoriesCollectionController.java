package com.disid.restful.web.category;

import com.disid.restful.datatables.DatatablesData;
import com.disid.restful.datatables.DatatablesPageable;
import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoriesCollectionController {

  public CategoryService categoryService;

  @Autowired
  public CategoriesCollectionController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // Create Categories

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity create(@Valid @RequestBody Category category, BindingResult result) {
    if (category.getId() != null) {
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    Category newCategory = categoryService.save(category);
    HttpHeaders responseHeaders = populateHeaders(newCategory.getId());
    return new ResponseEntity(newCategory, responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/create-form", method = RequestMethod.GET,
      produces = MediaType.TEXT_HTML_VALUE)
  public String createForm(Model model) {
    model.addAttribute(new Category());
    return "categories/create";
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
  public String create(@Valid @ModelAttribute Category category, BindingResult result,
      RedirectAttributes redirectAttrs, Model model) {
    if (result.hasErrors()) {
      return "categories/create";
    }
    Category newCategory = categoryService.save(category);
    redirectAttrs.addAttribute("id", newCategory.getId());
    return "redirect:/categories/{id}";
  }

  // List Categories

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
  public String list(Model model) {
    return "categories/list";
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Page<Category> list(GlobalSearch search, Pageable pageable) {
    Page<Category> category = categoryService.findAll(search, pageable);
    return category;
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/vnd.datatables+json")
  @ResponseBody
  public DatatablesData<Category> list(GlobalSearch search, DatatablesPageable pageable,
      @RequestParam("draw") Integer draw) {
    Page<Category> category = list(search, pageable);
    long allAvailableCategory = categoryService.count();
    return new DatatablesData<Category>(category, allAvailableCategory, draw);
  }

  // Batch operations with Categories

  @RequestMapping(value = "/batch", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity createBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Category> newCategorys = categoryService.save(categorys);
    return new ResponseEntity(newCategorys, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/batch", method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes", "unchecked"})
  public ResponseEntity updateBatch(@Valid @RequestBody Collection<Category> categorys,
      BindingResult result) {
    if (result.hasErrors()) {
      return new ResponseEntity(result, HttpStatus.CONFLICT);
    }
    List<Category> savedCategorys = categoryService.save(categorys);
    return new ResponseEntity(savedCategorys, HttpStatus.OK);
  }

  @RequestMapping(value = "/batch/{ids}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @SuppressWarnings({"rawtypes"})
  public ResponseEntity deleteBatch(@PathVariable("ids") Collection<Long> ids) {
    categoryService.delete(ids);
    return new ResponseEntity(HttpStatus.OK);
  }

  // Populates

  public HttpHeaders populateHeaders(Long id) {
    UriComponents uriComponents = UriComponentsBuilder.fromUriString("/categorys/{id}").build();
    URI uri = uriComponents.expand(id).encode().toUri();
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);
    return responseHeaders;
  }

}
