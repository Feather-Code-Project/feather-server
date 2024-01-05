package com.feathercode.domain.user.service;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.repository.UserRepository;
import com.feathercode.jwt.JwtTokenProvider;
import com.feathercode.jwt.TokenInfo;
import com.feathercode.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;



    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
    }


    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getTopReviewers() {
        return userRepository.getTopReviewers();
    }

    @Transactional
    public User updateNotionLink(Long userId, String notionLink) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateNotionLink(notionLink);

        return user;
    }

    @Transactional
    public User updateGithubLink(Long userId, String githubLink) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateGithubLink(githubLink);

        return user;
    }

    @Transactional
    public User updateContents(Long userId, String contents) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateContents(contents);

        return user;
    }

    public ResponseEntity<?> logout(TokenInfo logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return Response.badRequest("잘못된 요청입니다.");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return Response.ok("로그아웃 되었습니다.");
    }

    public ResponseEntity<?> reissue(TokenInfo reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return Response.badRequest("Refresh Token 정보가 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        Object o = redisTemplate.opsForValue().get("RT:" + authentication.getName());
        String refreshToken = (String) o;
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            return Response.badRequest("잘못된 요청입니다.");
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            return Response.badRequest("Refresh Token 정보가 일치하지 않습니다.");
        }

        // 4. 새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return Response.makeResponse(HttpStatus.OK, "토큰 재발급을 성공하였습니다.", 1, tokenInfo);
    }

}
