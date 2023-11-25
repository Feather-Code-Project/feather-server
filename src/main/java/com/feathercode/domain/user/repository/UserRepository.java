package com.feathercode.domain.user.repository;

import com.feathercode.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long>,CustomUserRepository {
    User findByUsername(String username);
    User findByNickname(String nickname);

    User findByEmail(String email);
    boolean existsByEmail(String email);
}
