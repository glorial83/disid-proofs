package com.disid.restful.repository;

import com.disid.restful.model.CustomerOrder;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = CustomerOrder.class, defaultSearchResult = CustomerOrder.class)
public interface CustomerOrderRepositoryCustom {
}
