package com.disid.restful.repository;
import com.disid.restful.model.Category;
import com.disid.restful.model.Product;
import com.disid.restful.model.QProduct;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.path.NumberPath;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ProductRepositoryImpl extends QueryDslRepositorySupportExt<Product> implements ProductRepositoryCustom {

    ProductRepositoryImpl() {
        super(Product.class);
    }

    private JPQLQuery getQueryFrom(QProduct qEntity) {
        return from(qEntity);
    }

    public Page<Product> findAllByCategories(Category category, GlobalSearch globalSearch, Pageable pageable) {
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

    public Page<Product> findAll(GlobalSearch globalSearch, Pageable pageable) {
        NumberPath<Long> idProduct = new NumberPath<Long>(Long.class, "id");
        QProduct product = QProduct.product;
        JPQLQuery query = getQueryFrom(product);
        BooleanBuilder where = new BooleanBuilder();
        if (globalSearch != null) {
            String txt = globalSearch.getText();
            where.and(product.name.containsIgnoreCase(txt).or(product.description.containsIgnoreCase(txt)));
        }
        query.where(where);
        long totalFound = query.count();
        if (pageable != null) {
            if (pageable.getSort() != null) {
                for (Sort.Order order : pageable.getSort()) {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                    switch(((order.getProperty()))) {
                        case "name":
                            query.orderBy(new OrderSpecifier<String>(direction, product.name));
                            break;
                        case "description":
                            query.orderBy(new OrderSpecifier<String>(direction, product.description));
                            break;
                    }
                }
            }
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        }
        query.orderBy(idProduct.asc());
        List<Product> results = query.list(product);
        return new PageImpl<Product>(results, pageable, totalFound);
    }
}
