/*
 * Copyright 2016 the original author or authors.
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
package org.springframework.roo.clinictests.config;

import io.springlets.data.jpa.repository.support.DetachableJpaRepositoryImpl;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.roo.clinictests.ClinictestsApplication;

/**
 * Configuration to set the Spring Data JPA repositories base class, using the 
 * {@link DetachableJpaRepositoryImpl} class provided by springlets-data-jpa.
 * 
 * Once you use the {@link EnableJpaRepositories} annotation, the default autoconfiguration
 * way to look for the repositories in the same or a child package as the main class isn't
 * performed anymore. That's why the _basePackageClasses_ has to be set here.
 * 
 * Another option would be to put the {@link EnableJpaRepositories} annotation in the main
 * class, but then the Spring Boot test slices, for example with the {@link WebMvcTest} annotation,
 * will apply the annotation and try to load the Spring Data JPA repositories.
 * 
 * @author Cèsar Ordiñana at http://www.disid.com[DISID Corporation S.L.]
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = ClinictestsApplication.class,
    repositoryBaseClass = DetachableJpaRepositoryImpl.class)
public class SpringDataJpaDetachableRepositoryConfiguration {
}
