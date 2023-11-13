package com.feathercode.domain.user.domain;

import com.feathercode.domain.model.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name="user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
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
}