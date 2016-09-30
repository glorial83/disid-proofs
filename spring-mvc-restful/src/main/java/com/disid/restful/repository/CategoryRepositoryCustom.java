package com.disid.restful.repository;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    Page<Category> findAllByProducts(Product product, GlobalSearch search, Pageable pageable);

    public abstract Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable);
}
