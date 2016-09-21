package com.disid.restful.web;

import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafMainController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooThymeleafMainController
@Controller
public class MainController {

  @RequestMapping(method = RequestMethod.GET, value = "/")
  public String index(Model model) {
    return "index";
  }
}
