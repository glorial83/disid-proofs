package com.disid.restful.repository;

import com.disid.restful.model.Category;
import com.disid.restful.model.QCategory;
import com.mysema.query.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CategoryRepositoryImpl extends QueryDslRepositorySupportExt<Category>
    implements CategoryRepositoryCustom {

  public CategoryRepositoryImpl() {
    super(Category.class);
  }

  public Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable) {
    QCategory category = QCategory.category;
    JPQLQuery query = from(category);
    applyGlobalSearch(globalSearch, query, category.name, category.description);
    applyPagination(pageable, query);
    applyOrderById(query);
    return loadPage(query, pageable, category);
  }
}
