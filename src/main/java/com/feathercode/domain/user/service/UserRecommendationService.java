package com.feathercode.domain.user.service;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.entity.UserRecommendation;
import com.feathercode.domain.user.repository.UserRecommendationRepository;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

    private final UserRepository userRepository;
    private final UserRecommendationRepository userRecommendationRepository;

    @Transactional
    public UserRecommendation save(Long toUserId, Long fromUserId) {
        User toUser = userRepository.findById(toUserId).orElseThrow();
        User fromUser = userRepository.findById(fromUserId).orElseThrow();

        if (userRecommendationRepository.existsByToUserAndFromUser(toUser, fromUser)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        UserRecommendation userRecommendation = UserRecommendation.builder()
                .toUser(toUser)
                .fromUser(fromUser)
                .build();

        userRepository.plusRecommendation(toUser);
        return userRecommendationRepository.save(userRecommendation);
    }

    @Transactional
    public void delete(Long userRecommendationId) {
        UserRecommendation userRecommendation = userRecommendationRepository.findById(userRecommendationId).orElseThrow();

        userRepository.minusRecommendation(userRecommendation.getToUser());
        userRecommendationRepository.delete(userRecommendation);
    }
}
