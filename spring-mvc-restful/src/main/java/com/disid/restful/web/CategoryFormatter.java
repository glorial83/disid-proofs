package com.disid.restful.web;

import com.disid.restful.model.Category;
import com.disid.restful.service.api.CategoryService;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.formatters.RooFormatter;

@RooFormatter(entity = Category.class, service = CategoryService.class)
public class CategoryFormatter {
}
