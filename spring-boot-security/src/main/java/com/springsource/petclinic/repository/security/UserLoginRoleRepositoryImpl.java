package com.springsource.petclinic.repository.security;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.path.NumberPath;
import com.springsource.petclinic.domain.security.LoginRole;
import com.springsource.petclinic.domain.security.QUserLoginRole;
import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginRole;
import com.springsource.petclinic.repository.GlobalSearch;
import com.springsource.petclinic.repository.QueryDslRepositorySupportExt;

@RooJpaRepositoryCustomImpl(repository = UserLoginRoleRepositoryCustom.class)
public class UserLoginRoleRepositoryImpl extends QueryDslRepositorySupportExt<UserLoginRole> {

  UserLoginRoleRepositoryImpl() {
    super(UserLoginRole.class);
  }
  
  public Page<UserLoginRole> findAll(GlobalSearch globalSearch, Pageable pageable) {
    NumberPath<Long> idUserLoginRole = new NumberPath<Long>(Long.class, "id");
    QUserLoginRole userLoginRole = QUserLoginRole.userLoginRole;
    JPQLQuery query = from(userLoginRole);
    BooleanBuilder where = new BooleanBuilder();

    if (globalSearch != null) {
        String txt = globalSearch.getText();
    }
    query.where(where);

    long totalFound = query.count();
    if (pageable != null) {
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    query.orderBy(idUserLoginRole.asc());
    
    List<UserLoginRole> results = query.list(userLoginRole);
    return new PageImpl<UserLoginRole>(results, pageable, totalFound);
}

public Page<UserLoginRole> findAllByUserLogin(UserLogin userLoginField, GlobalSearch globalSearch, Pageable pageable) {
    NumberPath<Long> idUserLoginRole = new NumberPath<Long>(Long.class, "id");
    QUserLoginRole userLoginRole = QUserLoginRole.userLoginRole;
    JPQLQuery query = from(userLoginRole);
    BooleanBuilder where = new BooleanBuilder(userLoginRole.userLogin.eq(userLoginField));

    if (globalSearch != null) {
        String txt = globalSearch.getText();
    }
    query.where(where);

    long totalFound = query.count();
    if (pageable != null) {
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    query.orderBy(idUserLoginRole.asc());
    
    List<UserLoginRole> results = query.list(userLoginRole);
    return new PageImpl<UserLoginRole>(results, pageable, totalFound);
}

public Page<UserLoginRole> findAllByLoginRole(LoginRole loginRoleField, GlobalSearch globalSearch, Pageable pageable) {
    NumberPath<Long> idUserLoginRole = new NumberPath<Long>(Long.class, "id");
    QUserLoginRole userLoginRole = QUserLoginRole.userLoginRole;
    JPQLQuery query = from(userLoginRole);
    BooleanBuilder where = new BooleanBuilder(userLoginRole.loginRole.eq(loginRoleField));

    if (globalSearch != null) {
        String txt = globalSearch.getText();
    }
    query.where(where);

    long totalFound = query.count();
    if (pageable != null) {
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    query.orderBy(idUserLoginRole.asc());
    
    List<UserLoginRole> results = query.list(userLoginRole);
    return new PageImpl<UserLoginRole>(results, pageable, totalFound);
}
}
