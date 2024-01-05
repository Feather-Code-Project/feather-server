package com.feathercode.domain.user.repository;

import com.feathercode.domain.model.ProviderType;
import com.feathercode.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,CustomUserRepository {
    User findByUsername(String username);
    User findByNickname(String nickname);

    User findUserByEmail(String email);
    User findByEmailAndProviderType(String email, ProviderType oAuthProviderType);
    boolean existsByEmailAndProviderType(String email, ProviderType oAuthProviderType);

}
