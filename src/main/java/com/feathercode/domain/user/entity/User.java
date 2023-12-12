package com.feathercode.domain.user.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.model.Gender;
import com.feathercode.domain.model.OauthProvider;
import com.feathercode.domain.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 20)
    @NotNull
    private String username;

    @Column(length = 12)
    private String nickname;

    @NotNull
    private String email;

    private String contents;
    private String notionLink;
    private String githubLink;
    private String userImage;

    @NotNull
    private int likesCnt;
    @NotNull
    private int recommendationsCnt;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private OauthProvider oauthProvider;

    @Builder
    public User(String username, String nickname, String email, String contents, String notionLink, String githubLink, String userImage, int likesCnt, int recommendationsCnt, Gender gender, Role role, OauthProvider oAuthProvider) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.contents = contents;
        this.notionLink = notionLink;
        this.githubLink = githubLink;
        this.userImage = userImage;
        this.likesCnt = likesCnt;
        this.recommendationsCnt = recommendationsCnt;
        this.gender = gender;
        this.role = role;
        this.oauthProvider=oAuthProvider;
    }

    public void join(String nickname, String contents, String notionLink, String githubLink, Gender gender) {
        this.nickname = nickname;
        this.contents = contents;
        this.notionLink = notionLink;
        this.githubLink = githubLink;
        this.gender = gender;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void updateNotionLink(String notionLink) {
        this.notionLink = notionLink;
    }

    public void updateGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
}