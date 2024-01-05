package com.feathercode.domain.user.controller;

import com.feathercode.domain.user.controller.dto.UserDto;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.service.UserService;
import com.feathercode.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

//    @PatchMapping("/join/{userId}")
//    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Valid @RequestBody JoinForm joinForm){
//        User user = userService.join(userId, joinForm.getNickname(), joinForm.getContents(), joinForm.getNotionLink(), joinForm.getGithubLink(), joinForm.getGender());
//
//        return ResponseEntity.ok(user);
//    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        userService.delete(user);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/findUser")
    public ResponseEntity<?> getUserById(@RequestParam Long userId) {
        User user = userService.findUserById(userId);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findByNickname")
    public ResponseEntity<?> getUserByNickname(@RequestParam String nickname) {
        User user = userService.findUserByNickname(nickname);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        User user = userService.findUserByUsername(username);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getTopReviewer")
    public ResponseEntity<?> getTopReviewer() {
        List<User> userList = userService.getTopReviewers();
        List<UserDto> userDtoList = userList.stream().map(UserDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(userDtoList);
    }

    @PatchMapping("/updateGithub/{userId}")
    public ResponseEntity<?> updateGithubLink(@PathVariable Long userId, String githubLink) {
        User user = userService.updateGithubLink(userId, githubLink);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/updateNotion/{userId}")
    public ResponseEntity<?> updateNotionLink(@PathVariable Long userId, String notionLink) {
        User user = userService.updateNotionLink(userId, notionLink);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/updateContents/{userId}")
    public ResponseEntity<?> updateContents(@PathVariable Long userId, String contents) {
        User user = userService.updateContents(userId, contents);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/reissue/test")
    public ResponseEntity reissue(@RequestBody TokenInfo reissue) {
        return userService.reissue(reissue);
    }
}