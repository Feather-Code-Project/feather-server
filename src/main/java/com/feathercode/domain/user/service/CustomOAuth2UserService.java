package com.feathercode.domain.user.service;

import com.feathercode.domain.model.Role;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = attributes.get("email").toString();

        if (exitsByEmail(email)) {
            User user =getByEmail(email);
            return createDefaultOAuth2User(user, attributes, userNameAttributeName);
        }else{
            User user = User.builder()
                    .email(email)
                    .username(attributes.get("name").toString())
                    .role(Role.USER)
                    .recommendationsCnt(0)
                    .likesCnt(0)
                    .build();
            saveUser(user);
            return createDefaultOAuth2User(user, attributes, userNameAttributeName);
        }
    }

    private DefaultOAuth2User createDefaultOAuth2User(User user, Map<String, Object> attributes,
                                                      String userNameAttributeName) {
        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey())),
                attributes,
                userNameAttributeName
        );
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean exitsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
