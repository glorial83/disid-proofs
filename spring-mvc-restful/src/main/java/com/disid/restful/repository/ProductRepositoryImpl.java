package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.model.QProduct;
import com.mysema.query.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

@RooJpaRepositoryCustomImpl(repository = ProductRepositoryCustom.class)
public class ProductRepositoryImpl extends QueryDslRepositorySupportExt<Product> {

  ProductRepositoryImpl() {
    super(Product.class);
  }

  private JPQLQuery getQueryFrom(QProduct qEntity) {
    return from(qEntity);
  }

  public Page<Product> findAllByCategories(Category category, GlobalSearch globalSearch,
      Pageable pageable) {
    QProduct product = QProduct.product;
    JPQLQuery query = from(product);

    if (category != null) {
      query.where(product.categories.contains(category));
    }
    applyGlobalSearch(globalSearch, query, product.name, product.description);
    applyPagination(pageable, query);
    applyOrderById(query);

    return loadPage(query, pageable, product);
  }
}
