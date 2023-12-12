package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUserRepository {
    void plusLike(User user);
    void minusLike(User user);

    void plusRecommendation(User user);
    void minusRecommendation(User user);
    List<User> getTopReviewers();
}
