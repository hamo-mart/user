package com.hamo.user.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QueryDslOrderUtils {

    private static final String DEFAULT_PROPERTY_NAME = "id";

    public static List<OrderSpecifier<?>> getOrderSpecifiers(
            Pageable pageable, PathBuilder<?> entityPath, String propertyId) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String[] properties = order.getProperty().split("\\.");

            PathBuilder<?> currentPath = entityPath;
            for (int i = 0; i < properties.length - 1; i++) {
                currentPath = currentPath.get(properties[i]);
            }

            ComparableExpressionBase<?> expression =
                    currentPath.getComparable(properties[properties.length - 1], Comparable.class);

            if (expression != null) {
                orders.add(new OrderSpecifier<>(direction, expression));
            }
        }

        if (orders.isEmpty()) {
            orders.add(new OrderSpecifier<>(Order.DESC,
                    entityPath.getComparable(propertyId, Comparable.class)));
        }

        return orders;
    }

    public static List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable, PathBuilder<?> entityPath) {
        return getOrderSpecifiers(pageable, entityPath, DEFAULT_PROPERTY_NAME);
    }

}
