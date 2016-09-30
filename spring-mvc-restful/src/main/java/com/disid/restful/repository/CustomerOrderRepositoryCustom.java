package com.disid.restful.repository;
import com.disid.restful.model.Customer;
import com.disid.restful.model.CustomerOrder;
import com.disid.restful.model.OrderDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerOrderRepositoryCustom {

    Page<OrderDetail> findDetailsByCustomerOrder(CustomerOrder customerOrderField, GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<CustomerOrder> findAll(GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<CustomerOrder> findAllByCustomer(Customer customerField, GlobalSearch globalSearch, Pageable pageable);
}
