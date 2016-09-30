package com.disid.restful.repository;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> findAllByCategories(Category category, GlobalSearch search, Pageable pageable);

    public abstract Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable);
}
