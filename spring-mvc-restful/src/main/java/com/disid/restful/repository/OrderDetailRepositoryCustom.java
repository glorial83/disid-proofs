package com.disid.restful.repository;

import com.disid.restful.model.OrderDetail;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;

@RooJpaRepositoryCustom(entity = OrderDetail.class, defaultSearchResult = OrderDetail.class)
public interface OrderDetailRepositoryCustom {
}
