package com.feathercode.domain.user.service;

import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 이건 OAUTH하고서 아마 수정 있을듯
     */

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->new NotFoundException(userId+"가 없습니다"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getTopReviewers(){
        return userRepository.getTopReviewers();
    }

    @Transactional
    public User updateNotionLink(Long userId,String notionLink){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateNotionLink(notionLink);

        return user;
    }

    @Transactional
    public User updateGithubLink(Long userId,String githubLink){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateGithubLink(githubLink);

        return user;
    }

    @Transactional
    public User updateContents(Long userId,String contents){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다."));
        user.updateContents(contents);

        return user;
    }
}
