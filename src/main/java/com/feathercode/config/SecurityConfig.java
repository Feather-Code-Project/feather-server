package com.feathercode.config;

import com.feathercode.config.oauth.OAuth2AuthenticationSuccessHandler;
import com.feathercode.config.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.feathercode.domain.user.service.CustomOAuth2UserService;
import com.feathercode.jwt.JwtAuthenticationFilter;
import com.feathercode.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
//@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final CustomOAuth2UserService userService;;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/login"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowedUrls).permitAll()
//                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().disable()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(userService);

        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                jwtTokenProvider,
                redisTemplate);
    }
}
/*todo
1. 유효하지 않은 토큰의 경우 invalid token에 대한 예외로직은 실행되지만 정상적인 흐름으로 흘러가는 것을 해결
 */