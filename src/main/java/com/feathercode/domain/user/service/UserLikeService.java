package com.feathercode.domain.user.service;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.entity.UserLike;
import com.feathercode.domain.user.repository.UserLikeRepository;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final UserRepository userRepository;
    private final UserLikeRepository userLikeRepository;

    @Transactional
    public void save(Long toUserId, Long fromUserId) {
        User toUser = userRepository.findById(toUserId).orElseThrow();
        User fromUser = userRepository.findById(fromUserId).orElseThrow();

        if (userLikeRepository.existsByToUserAndFromUser(toUser, fromUser)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        UserLike userLike = UserLike.builder().
                toUser(toUser)
                .fromUser(fromUser)
                .build();

        userLikeRepository.save(userLike);
        userRepository.plusLike(toUser);
    }

    @Transactional
    public void delete(Long userLikeId) {
        UserLike userLike = userLikeRepository.findById(userLikeId).orElseThrow();

        userRepository.minusLike(userLike.getToUser());
        userLikeRepository.delete(userLike);
    }
}
