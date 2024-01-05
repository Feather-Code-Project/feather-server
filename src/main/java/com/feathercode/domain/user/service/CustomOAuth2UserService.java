package com.feathercode.domain.user.service;

import com.feathercode.config.oauth.OAuth2UserInfo;
import com.feathercode.config.oauth.OAuth2UserInfoFactory;
import com.feathercode.config.oauth.entity.UserPrincipal;
import com.feathercode.config.oauth.exception.OAuthProviderMissMatchException;
import com.feathercode.domain.model.ProviderType;
import com.feathercode.domain.model.Role;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String ALREADY_SIGNED_UP_SOCIAL = "already_signed_up_social";
    private static final String ALREADY_SIGNED_UP_LOCAL = "already_signed_up_local";

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            OAuth2User process = process(userRequest, user);
            return process;
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("CustomOAuth2UserService loadUser Error: {} ", ex.getMessage());
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findUserByEmail(userInfo.getEmail());

        if (savedUser != null) {
            if (savedUser.getProviderType() == ProviderType.LOCAL){
                log.error("CustomOAuth2UserService process Error: 기존 회원입니다. 자체 로그인을 이용해 주세요. ");
                throw new OAuthProviderMissMatchException(ALREADY_SIGNED_UP_LOCAL);
            }
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .nickname(userInfo.getName())
                .providerType(providerType)
                .role(Role.USER)
                .build();

        return userRepository.saveAndFlush(user);
    }

}






//
//import com.feathercode.domain.model.ProviderType;
//import com.feathercode.domain.model.Role;
//import com.feathercode.domain.user.entity.User;
//import com.feathercode.domain.user.model.OAuthAttributes;
//import com.feathercode.domain.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Map;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String oauthProvider = userRequest.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        OAuthAttributes oAuthAttributes = OAuthAttributes.of(oauthProvider, userNameAttributeName, attributes);
//        User user = saveUser(oAuthAttributes, oauthProvider);
//        return createDefaultOAuth2User(user, attributes, userNameAttributeName);
//    }
//
//
//    private DefaultOAuth2User createDefaultOAuth2User(User user, Map<String, Object> attributes,
//                                                      String userNameAttributeName) {
//        return new DefaultOAuth2User(
//                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey())),
//                attributes,
//                userNameAttributeName
//        );
//    }
//
//    public User saveUser(OAuthAttributes oAuthAttributes, String oauthProvider) {
//        if (existsByEmailAndOAuthProvider(oAuthAttributes.getEmail(), ProviderType.valueOf(oauthProvider.toUpperCase()))) {
//            return getByEmailAndOAuthProvider(oAuthAttributes.getEmail(), ProviderType.valueOf(oauthProvider.toUpperCase()));
//        } else {
//            User user = User.builder()
//                    .email(oAuthAttributes.getEmail())
//                    .username(oAuthAttributes.getName())
//                    .role(Role.USER)
//                    .recommendationsCnt(0)
//                    .likesCnt(0)
//                    .providerType(ProviderType.valueOf(oauthProvider.toUpperCase()))
//                    .build();
//            userRepository.save(user);
//
//            return user;
//        }
//    }
//
//    public User getByEmailAndOAuthProvider(String email, ProviderType providerType) {
//        return userRepository.findByEmailAndProviderType(email, providerType);
//    }
//
//    public Boolean existsByEmailAndOAuthProvider(String email, ProviderType providerType) {
//        return userRepository.existsByEmailAndProviderType(email, providerType);
//    }
//}