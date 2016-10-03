package com.disid.restful.http.converter.json;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.annotations.http.converters.json.RooJSONExceptionHandlerAdvice;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RooJSONExceptionHandlerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(BindingErrorException.class)
  @ResponseBody
  public ResponseEntity<?> handleException(HttpServletRequest req, BindingErrorException ex) {
    BindingResult result = ex.getBindingResult();
    return ResponseEntity.unprocessableEntity().body(result);
  }
}
