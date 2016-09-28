package com.springsource.petclinic.repository.security;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.NumberPath;
import com.springsource.petclinic.domain.security.LoginRole;
import com.springsource.petclinic.domain.security.QLoginRole;
import com.springsource.petclinic.repository.GlobalSearch;
import com.springsource.petclinic.repository.QueryDslRepositorySupportExt;

@RooJpaRepositoryCustomImpl(repository = LoginRoleRepositoryCustom.class)
public class LoginRoleRepositoryImpl extends QueryDslRepositorySupportExt<LoginRole> {

  LoginRoleRepositoryImpl() {
    super(LoginRole.class);
  }
  
  public Page<LoginRole> findAll(GlobalSearch globalSearch, Pageable pageable) {
    NumberPath<Long> idLoginRole = new NumberPath<Long>(Long.class, "id");
    QLoginRole loginRole = QLoginRole.loginRole;
    JPQLQuery query = from(loginRole);
    BooleanBuilder where = new BooleanBuilder();

    if (globalSearch != null) {
        String txt = globalSearch.getText();
        where.and(
            loginRole.name.containsIgnoreCase(txt)
            .or(loginRole.description.containsIgnoreCase(txt))
        );

    }
    query.where(where);

    long totalFound = query.count();
    if (pageable != null) {
        if (pageable.getSort() != null) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                switch(order.getProperty()){
                    case "name":
                       query.orderBy(new OrderSpecifier<String>(direction, loginRole.name));
                       break;
                    case "description":
                       query.orderBy(new OrderSpecifier<String>(direction, loginRole.description));
                       break;
                }
            }
        }
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    query.orderBy(idLoginRole.asc());
    
    List<LoginRole> results = query.list(loginRole);
    return new PageImpl<LoginRole>(results, pageable, totalFound);
}


}
