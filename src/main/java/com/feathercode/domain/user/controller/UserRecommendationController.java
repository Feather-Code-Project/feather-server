package com.feathercode.domain.user.controller;

import com.feathercode.domain.user.service.UserRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userRecommendation")
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;

    @PostMapping("/{toUserId}/{fromUserId}")
    public ResponseEntity<?> save(@PathVariable Long toUserId, @PathVariable Long fromUserId) {
        userRecommendationService.save(toUserId, fromUserId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userLikeId}")
    public ResponseEntity<?> delete(@PathVariable Long userLikeId) {
        userRecommendationService.delete(userLikeId);
        return ResponseEntity.ok(null);
    }
}
