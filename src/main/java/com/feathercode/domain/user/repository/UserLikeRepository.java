package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike,Long> {
    boolean existsByToUserAndFromUser(User toUser, User fromUser);
}
