package com.feathercode.domain.user.controller.dto;

import com.feathercode.domain.model.Gender;
import com.feathercode.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserDto {
    private String username;
    private String nickname;
    private String email;
    private String contents;
    private String notionLink;
    private String githubLink;
    private String userImage;
    private int likesCnt;
    private int recommendationsCnt;
    private Gender gender;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.contents = user.getContents();
        this.notionLink = user.getNotionLink();
        this.githubLink = user.getGithubLink();
        this.userImage = user.getUserImage();
        this.likesCnt = user.getLikesCnt();
        this.recommendationsCnt = user.getRecommendationsCnt();
        this.gender = user.getGender();
    }
}
