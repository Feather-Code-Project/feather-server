package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.feathercode.domain.user.entity.QUser.user;
import static com.feathercode.domain.user.entity.QUserRecommendation.userRecommendation;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements CustomUserRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public void plusLike(User selectUser) {
        jpaQueryFactory.update(user)
                .set(user.likesCnt,user.likesCnt.add(1))
                .where(user.eq(selectUser))
                .execute();
    }

    @Override
    public void minusLike(User selectUser) {
        jpaQueryFactory.update(user)
                .set(user.likesCnt,user.likesCnt.subtract(1))
                .where(user.eq(selectUser))
                .execute();
    }

    @Override
    public void plusRecommendation(User selectUser) {
        jpaQueryFactory.update(user)
                .set(user.recommendationsCnt,user.recommendationsCnt.add(1))
                .where(user.eq(selectUser))
                .execute();
    }

    @Override
    public void minusRecommendation(User selectUser) {
        jpaQueryFactory.update(user)
                .set(user.recommendationsCnt,user.recommendationsCnt.subtract(1))
                .where(user.eq(selectUser))
                .execute();
    }

    @Override
    public List<User> getTopReviewers() {
        LocalDateTime date = LocalDateTime.now().minusDays(3);

        List<Tuple> result = jpaQueryFactory
                .select(userRecommendation.toUser, userRecommendation.toUser.recommendationsCnt.sum())
                .from(userRecommendation)
                .where(userRecommendation.createdAt.after(date))
                .groupBy(userRecommendation.toUser)
                .orderBy(userRecommendation.toUser.recommendationsCnt.sum().desc())
                .limit(5)
                .fetch();

        return result.stream()
                .map(tuple -> tuple.get(userRecommendation.toUser))
                .collect(Collectors.toList());
    }
}
