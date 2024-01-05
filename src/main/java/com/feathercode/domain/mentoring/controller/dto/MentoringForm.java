package com.feathercode.domain.mentoring.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MentoringForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String duty;
    private String career;
    @NotNull
    private int price;

    @NotBlank
    private String contact;
    private String introductionLink;
}
