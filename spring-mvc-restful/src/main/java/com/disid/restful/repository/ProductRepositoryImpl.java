package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.model.ProductByNameAndDescriptionSearchForm;
import com.disid.restful.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly = true)
public class ProductRepositoryImpl extends QueryDslRepositorySupportExt<Product>
    implements ProductRepositoryCustom {

  ProductRepositoryImpl() {
    super(Product.class);
  }

  public Page<Product> findByCategoriesContains(Category category, GlobalSearch globalSearch,
      Pageable pageable) {
    QProduct product = QProduct.product;
    JPQLQuery<Product> query = from(product);
    if (category != null) {
      query.where(product.categories.contains(category));
    }
    applyGlobalSearch(globalSearch, query, product.name, product.description);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, product);
  }

  public Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QProduct product = QProduct.product;
    JPQLQuery<Product> query = from(product);
    applyGlobalSearch(globalSearch, query, product.name, product.description);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, product);
  }

  @Override
  public long countByNameAndDescription(ProductByNameAndDescriptionSearchForm formBean) {
    return queryByNameAndDescription(formBean).fetchCount();
  }

  @Override
  public Page<Product> findByNameAndDescription(ProductByNameAndDescriptionSearchForm formBean,
      GlobalSearch globalSearch, Pageable pageable) {
    JPQLQuery<Product> query = queryByNameAndDescription(formBean);

    QProduct product = QProduct.product;
    applyGlobalSearch(globalSearch, query, product.name, product.description);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, product);
  }

  private JPQLQuery<Product> queryByNameAndDescription(
      ProductByNameAndDescriptionSearchForm formBean) {
    QProduct product = QProduct.product;
    JPQLQuery<Product> query = from(product);
    BooleanBuilder searchCondition = new BooleanBuilder();
    if (StringUtils.hasText(formBean.getName())) {
      searchCondition.and(product.name.containsIgnoreCase(formBean.getName()));
    }
    if (StringUtils.hasText(formBean.getDescription())) {
      searchCondition.and(product.description.containsIgnoreCase(formBean.getDescription()));
    }
    return query.where(searchCondition);
  }

}
