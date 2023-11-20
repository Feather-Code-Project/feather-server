package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long>,CustomUserRepository {
    User findByUsername(String username);
    User findByNickname(String nickname);

}
