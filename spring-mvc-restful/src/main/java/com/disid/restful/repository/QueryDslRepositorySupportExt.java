package com.disid.restful.repository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.OrderSpecifier.NullHandling;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;
import java.util.List;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

public class QueryDslRepositorySupportExt<T> extends QueryDslRepositorySupport {

    private final Class<T> domainClass;

    public QueryDslRepositorySupportExt(Class<T> domainClass) {
        super(domainClass);
        this.domainClass = domainClass;
    }

    protected JPQLQuery applyPagination(Pageable pageable, JPQLQuery query) {
        return getQuerydsl().applyPagination(pageable, query);
    }

    protected JPQLQuery applySorting(Sort sort, JPQLQuery query) {
        return getQuerydsl().applySorting(sort, query);
    }

    protected JPQLQuery applyOrderById(JPQLQuery query) {
        return applyOrderById(query, domainClass);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <M> JPQLQuery applyOrderById(JPQLQuery query, Class<M> entityClass) {
        EntityType<M> entity = getEntityMetaModel(entityClass);
        SingularAttribute<?, ?> id = entity.getId(entity.getIdType().getJavaType());
        PathBuilder<Object> idPath = getBuilder().get(id.getName());
        return query.orderBy(new OrderSpecifier(Order.ASC, idPath, NullHandling.NullsFirst));
    }

    protected JPQLQuery applyGlobalSearch(GlobalSearch globalSearch, JPQLQuery query, Path<?>... globalSearchAttributes) {
        if (globalSearch != null && !StringUtils.isEmpty(globalSearch.getText()) && globalSearchAttributes.length > 0) {
            String txt = globalSearch.getText();
            BooleanBuilder searchCondition = new BooleanBuilder();
            for (int i = 0; i < globalSearchAttributes.length; i++) {
                Path<?> path = globalSearchAttributes[i];
                if (path instanceof StringPath) {
                    StringPath stringPath = (StringPath) path;
                    searchCondition.or(stringPath.containsIgnoreCase(txt));
                } else if (path instanceof NumberExpression) {
                    searchCondition.or(((NumberExpression<?>) path).like("%".concat(txt).concat("%")));
                }
            }
            return query.where(searchCondition);
        }
        return query;
    }

    protected <M> Page<M> loadPage(JPQLQuery query, Pageable pageable, EntityPath<M> path) {
        long totalFound = query.count();
        List<M> results = query.list(path);
        return new PageImpl<M>(results, pageable, totalFound);
    }

    private <M> EntityType<M> getEntityMetaModel(Class<M> entityClass) {
        Metamodel metamodel = getEntityManager().getMetamodel();
        return metamodel.entity(entityClass);
    }
}
