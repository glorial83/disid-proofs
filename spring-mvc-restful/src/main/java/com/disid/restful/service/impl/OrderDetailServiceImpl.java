package com.disid.restful.service.impl;

import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;
import com.disid.restful.model.OrderDetailPK;
import com.disid.restful.model.Product;
import com.disid.restful.repository.GlobalSearch;
import com.disid.restful.repository.OrderDetailRepository;
import com.disid.restful.service.api.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RooServiceImpl(service = OrderDetailService.class)
@Service
@Transactional(readOnly = true)
public class OrderDetailServiceImpl implements OrderDetailService {

  public void delete(OrderDetail orderDetail) {
    orderDetailRepository.delete(orderDetail);
  }

  public OrderDetailRepository orderDetailRepository;

  @Autowired
  public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
    this.orderDetailRepository = orderDetailRepository;
  }

  @Transactional(readOnly = false)
  public OrderDetail save(OrderDetail entity) {
    return orderDetailRepository.save(entity);
  }

  @Transactional(readOnly = false)
  public void delete(OrderDetailPK id) {
    orderDetailRepository.delete(id);
  }

  @Transactional(readOnly = false)
  public List<OrderDetail> save(Iterable<OrderDetail> entities) {
    return orderDetailRepository.save(entities);
  }

  @Transactional(readOnly = false)
  public void delete(Iterable<OrderDetailPK> ids) {
    List<OrderDetail> toDelete = orderDetailRepository.findAll(ids);
    orderDetailRepository.deleteInBatch(toDelete);
  }

  public List<OrderDetail> findAll() {
    return orderDetailRepository.findAll();
  }

  public List<OrderDetail> findAll(Iterable<OrderDetailPK> ids) {
    return orderDetailRepository.findAll(ids);
  }

  public OrderDetail findOne(OrderDetailPK id) {
    return orderDetailRepository.findOne(id);
  }

  public long count() {
    return orderDetailRepository.count();
  }

  public Page<OrderDetail> findAll(GlobalSearch globalSearch, Pageable pageable) {
    return orderDetailRepository.findAll(globalSearch, pageable);
  }

  public Long countByProductId(Long id) {
    return orderDetailRepository.countByProductId(id);
  }

  public Long countByCustomerOrderId(Long id) {
    return orderDetailRepository.countByCustomerOrderId(id);
  }

  public Page<OrderDetail> findAllByProduct(Product productField, GlobalSearch globalSearch,
      Pageable pageable) {
    return orderDetailRepository.findAllByProduct(productField, globalSearch, pageable);
  }

  public Page<OrderDetail> findAllByCustomerOrder(CustomerOrder customerOrderField,
      GlobalSearch globalSearch, Pageable pageable) {
    return orderDetailRepository.findAllByCustomerOrder(customerOrderField, globalSearch, pageable);
  }
}
