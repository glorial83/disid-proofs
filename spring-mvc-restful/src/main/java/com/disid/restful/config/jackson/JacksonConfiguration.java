/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.disid.restful.config.jackson;

import com.disid.restful.http.converter.json.BindingResultSerializer;
import com.disid.restful.http.converter.json.FieldErrorSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Configures custom serialization and deserialization of entities.
 *
 * @author Cèsar Ordiñana
 */
@Configuration
class JacksonConfiguration {

  //  @Autowired
  //  private CustomerService customerService;
  //
  //  @Autowired
  //  private ConversionService conversionService;

  @Bean
  public Module modelModule() {
    return new DomainModelModule();
  }

  //  @Bean
  //  public CustomerDeserializer customerDeserializer() {
  //    return new CustomerDeserializer(new CustomerFormatter(customerService, conversionService));
  //  }


  @Bean
  public Module bindingResultModule() {

    SimpleModule module = new SimpleModule();

    module.addSerializer(BindingResult.class, new BindingResultSerializer());
    module.addSerializer(FieldError.class, new FieldErrorSerializer());

    return module;
  }
}
