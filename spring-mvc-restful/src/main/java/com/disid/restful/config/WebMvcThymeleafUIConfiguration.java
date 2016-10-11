package com.disid.restful.config;

import io.springlets.data.web.datatables.DatatablesPageableHandlerMethodArgumentResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcThymeleafUIConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(datatablesPageableResolver());
  }

  @Bean
  public DatatablesPageableHandlerMethodArgumentResolver datatablesPageableResolver() {
    return new DatatablesPageableHandlerMethodArgumentResolver();
  }
}
