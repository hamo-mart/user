package com.hamo.user.repository.user.impl;

import com.hamo.user.domain.role.QRole;
import com.hamo.user.domain.user.QUser;
import com.hamo.user.domain.user.User;
import com.hamo.user.domain.userroll.QUserRole;
import com.hamo.user.dto.user.QUserResponse;
import com.hamo.user.dto.user.UserResponse;
import com.hamo.user.repository.user.UserRepositoryCustom;
import com.hamo.user.util.QueryDslOrderUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<UserResponse> findUsers(Pageable pageable) {

        QUser user = QUser.user;
        QRole role = QRole.role;
        QUserRole userRole = QUserRole.userRole;

        PathBuilder<User> pathUser = new PathBuilder<>(User.class, "user");

        List<UserResponse> users = jpaQueryFactory.select(
                        new QUserResponse(
                                user.id,
                                user.email,
                                user.nickname
                        )
                ).from(user)
                .orderBy(QueryDslOrderUtils.getOrderSpecifiers(pageable, pathUser).toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> userIds = users.stream()
                .map(UserResponse::getId)
                .toList();

        Map<Long, Set<String>> userIdToRole = jpaQueryFactory
                .select(user.id, role.name)
                .from(userRole)
                .join(role).on(userRole.id.eq(role.id))
                .where(userRole.user.id.in(userIds))
                .fetch()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                tuple -> tuple.get(userRole.user.id),
                                Collectors.mapping(tuple -> tuple.get(role.name),Collectors.toSet())
                        )
                );

        users.forEach(
                userResponse -> userResponse.setRoles(userIdToRole.get(userResponse.getId()))
        );

        return new PageImpl<>(users, pageable,users.size());
    }

    @Override
    public UserResponse findUserById(Long userId) {

        QUser user = QUser.user;
        QRole role = QRole.role;
        QUserRole userRole = QUserRole.userRole;

        UserResponse userResponse = jpaQueryFactory.select(
                new QUserResponse(
                        user.id,
                        user.email,
                        user.nickname
                )
        ).from(user).where(user.id.eq(userId))
                .fetchOne();

        Set<String> userRoles = new HashSet<>(jpaQueryFactory
                .select(role.name)
                .from(userRole)
                .join(role).on(role.id.eq(userRole.role.id))
                .where(userRole.user.id.eq(userId))
                .fetch());
        if (userResponse != null) {
            userResponse.setRoles(userRoles);
        }
        return userResponse;
    }
}
