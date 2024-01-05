package com.feathercode.config.oauth.entity;

import com.feathercode.domain.model.ProviderType;
import com.feathercode.domain.model.Role;
import com.feathercode.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails, OAuth2User {
    private final String email;
    private final String nickname;
    private final String password;
    private final ProviderType providerType;
    private final Role roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return getAttributes().toString();
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getEmail(),
                user.getNickname(),
                user.getPassword(),
                user.getProviderType(),
                Role.USER,
                Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name()))
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }
}


/*
//기존 코드
public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getEmail(),
                user.getNickname(),
                user.getPassword(),
                user.getProviderType(),
                Role.USER,
                Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name()))
        );
    }
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }
 */