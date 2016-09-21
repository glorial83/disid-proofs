package com.disid.restful.service.api;

import org.springframework.roo.addon.layers.service.annotations.RooService;

import com.disid.restful.model.OrderDetail;

@RooService(entity = OrderDetail.class)
public interface OrderDetailService {

  void delete(OrderDetail orderDetail);
}
