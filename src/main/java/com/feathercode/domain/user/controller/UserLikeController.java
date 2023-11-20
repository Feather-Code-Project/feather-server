package com.feathercode.domain.user.controller;

import com.feathercode.domain.user.service.UserLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userLike")
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping("/{toUserId}/{fromUserId}")
    public ResponseEntity<?> save(@PathVariable Long toUserId, @PathVariable Long fromUserId) {
        userLikeService.save(toUserId, fromUserId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{userLikeId}")
    public ResponseEntity<?> delete(@PathVariable Long userLikeId) {
        userLikeService.delete(userLikeId);
        return ResponseEntity.ok(null);
    }
}
