package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.entity.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Long>{
   boolean existsByToUserAndFromUser(User toUser, User fromUser);
}
