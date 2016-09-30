package com.disid.restful.repository;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    long countByCategoriesContains(Category category);
}
