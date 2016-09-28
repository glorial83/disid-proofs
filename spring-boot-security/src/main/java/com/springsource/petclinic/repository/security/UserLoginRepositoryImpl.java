package com.springsource.petclinic.repository.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.springsource.petclinic.domain.security.QUserLogin;
import com.springsource.petclinic.domain.security.UserLogin;
import com.springsource.petclinic.domain.security.UserLoginDetails;
import com.springsource.petclinic.domain.security.UserLoginRole;
import com.springsource.petclinic.repository.GlobalSearch;
import com.springsource.petclinic.repository.QueryDslRepositorySupportExt;

@RooJpaRepositoryCustomImpl(repository = UserLoginRepositoryCustom.class)
public class UserLoginRepositoryImpl extends QueryDslRepositorySupportExt<UserLogin> {

  UserLoginRepositoryImpl() {
    super(UserLogin.class);
  }

  @Override
  public UserLoginDetails findByUsername(UserLogin login) {
    QUserLogin user = QUserLogin.userLogin;
    UserLogin userLogin =
        from(user).where(user.username.eq(login.getUsername())).singleResult(user);
    UserLoginDetails details = null;
    if (userLogin != null) {
      Set<UserLoginRole> userLoginRoles = userLogin.getUserLoginRoles();
      Set<String> roleNames = null;
      if (userLoginRoles != null) {
        roleNames = new HashSet<String>(userLoginRoles.size());
        for (UserLoginRole userLoginRole : userLoginRoles) {
          roleNames.add(userLoginRole.getLoginRole().getName());
        }
      }
      details = new UserLoginDetails(userLogin.getId(), userLogin.getUsername(),
          userLogin.getPassword(), roleNames);
    }

    return details;
  }
  
  public Page<UserLogin> findAll(GlobalSearch globalSearch, Pageable pageable) {
    NumberPath<Long> idUserLogin = new NumberPath<Long>(Long.class, "id");
    QUserLogin userLogin = QUserLogin.userLogin;
    JPQLQuery query = from(userLogin);
    BooleanBuilder where = new BooleanBuilder();

    if (globalSearch != null) {
        String txt = globalSearch.getText();
        where.and(
            userLogin.username.containsIgnoreCase(txt)
            .or(userLogin.password.containsIgnoreCase(txt))
        );

    }
    query.where(where);

    long totalFound = query.count();
    if (pageable != null) {
        if (pageable.getSort() != null) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                switch(order.getProperty()){
                    case "username":
                       query.orderBy(new OrderSpecifier<String>(direction, userLogin.username));
                       break;
                    case "password":
                       query.orderBy(new OrderSpecifier<String>(direction, userLogin.password));
                       break;
                }
            }
        }
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }
    query.orderBy(idUserLogin.asc());
    
    List<UserLogin> results = query.list(userLogin);
    return new PageImpl<UserLogin>(results, pageable, totalFound);
}


}
