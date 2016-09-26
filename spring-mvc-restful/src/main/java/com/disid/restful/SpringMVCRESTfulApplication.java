package com.disid.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;

@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public class SpringMVCRESTfulApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringMVCRESTfulApplication.class, args);
  }
}
