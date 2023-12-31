package com.feathercode.domain.user.repository;

import com.feathercode.domain.model.OauthProvider;
import com.feathercode.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,CustomUserRepository {
    User findByUsername(String username);
    User findByNickname(String nickname);

    User findByEmail(String email);

    User findByEmailAndOauthProvider(String email, OauthProvider oAuthProvider);
    boolean existsByEmailAndOauthProvider(String email, OauthProvider oAuthProvider);
}
