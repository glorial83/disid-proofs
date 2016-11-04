package com.disid.restful.web;

import io.springlets.web.NotFoundException;

import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafMainController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooThymeleafMainController
@Controller
public class MainController {

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Model model) {
    return "index";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/js/{template}.js")
  public String javascriptTemplates(@PathVariable("template") String template) {
    // TODO: add browser cache management
    if (StringUtils.hasLength(template)) {
      return template.concat(".js");
    }
    throw new NotFoundException("File not found");
  }
}
