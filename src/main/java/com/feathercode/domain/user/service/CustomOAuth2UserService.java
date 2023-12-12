package com.feathercode.domain.user.service;

import com.feathercode.domain.model.OauthProvider;
import com.feathercode.domain.model.Role;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.model.OAuthAttributes;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String oauthProvider = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(oauthProvider, userNameAttributeName, attributes);
        User user = saveUser(oAuthAttributes, oauthProvider);
        return createDefaultOAuth2User(user, attributes, userNameAttributeName);
    }


    private DefaultOAuth2User createDefaultOAuth2User(User user, Map<String, Object> attributes,
                                                      String userNameAttributeName) {
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes,
                userNameAttributeName
        );
    }

    public User saveUser(OAuthAttributes oAuthAttributes, String oauthProvider) {
        if (existsByEmailAndOAuthProvider(oAuthAttributes.getEmail(), OauthProvider.valueOf(oauthProvider.toUpperCase()))) {
            return getByEmailAndOAuthProvider(oAuthAttributes.getEmail(), OauthProvider.valueOf(oauthProvider.toUpperCase()));
        } else {
            User user = User.builder()
                    .email(oAuthAttributes.getEmail())
                    .username(oAuthAttributes.getName())
                    .role(Role.USER)
                    .recommendationsCnt(0)
                    .likesCnt(0)
                    .oAuthProvider(OauthProvider.valueOf(oauthProvider.toUpperCase()))
                    .build();
            userRepository.save(user);

            return user;
        }
    }

    public User getByEmailAndOAuthProvider(String email, OauthProvider oAuthProvider) {
        return userRepository.findByEmailAndOauthProvider(email, oAuthProvider);
    }

    public Boolean existsByEmailAndOAuthProvider(String email, OauthProvider oAuthProvider) {
        return userRepository.existsByEmailAndOauthProvider(email, oAuthProvider);
    }
}
