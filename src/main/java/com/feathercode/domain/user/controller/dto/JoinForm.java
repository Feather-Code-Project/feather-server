package com.feathercode.domain.user.controller.dto;

import com.feathercode.domain.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JoinForm {

    @NotBlank
    private String nickname;
    private String contents;
    private String notionLink;
    private String githubLink;

    @NotNull
    private Gender gender;

    public JoinForm(String nickname, String contents, String notionLink, String githubLink, Gender gender) {
        this.nickname = nickname;
        this.contents = contents;
        this.notionLink = notionLink;
        this.githubLink = githubLink;
        this.gender = gender;
    }
}
