package com.feathercode.domain.user.entity;


import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.model.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name="user")
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
    @NotNull
    private String nickname;

    @Column(unique = true)
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

    @Builder
    public User(String username, String nickname, String email, String contents, String notionLink, String githubLink, String userImage, int likesCnt, int recommendationsCnt, Gender gender) {
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
    }

    public void updateContents(String contents){
        this.contents=contents;
    }

    public void updateNotionLink(String notionLink){
        this.notionLink=notionLink;
    }

    public void updateGithubLink(String githubLink){
        this.githubLink=githubLink;
    }
}