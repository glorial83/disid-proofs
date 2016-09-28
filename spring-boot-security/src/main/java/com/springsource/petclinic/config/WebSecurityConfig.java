package com.springsource.petclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // Configuring Content Security Policy
    http.headers()
        .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy",
            "script-src 'self' 'unsafe-inline' "))
        .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy",
            "script-src 'self' 'unsafe-inline' "))
        .addHeaderWriter(
            new StaticHeadersWriter("X-WebKit-CSP", "script-src 'self' 'unsafe-inline' "));

    // Access management
    http.authorizeRequests()

        // static resources
        .antMatchers("/public/**", "/webjars/**").permitAll()

        // All the other requests nned to be authenticated
        .anyRequest().authenticated()

        // Configuring form login page
        .and().formLogin().loginPage("/login").permitAll();

  }

}
