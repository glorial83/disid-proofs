package com.disid.restful.repository;

import com.disid.restful.model.Category;

import io.springlets.data.domain.GlobalSearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

  public abstract Page<Category> findAll(GlobalSearch globalSearch, Pageable pageable);
}
