package com.feathercode.domain.mentoring.service.dto;

import com.feathercode.domain.mentoring.entity.Mentoring;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MentoringDto {

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

    public MentoringDto(Mentoring mentoring) {
        this.title = mentoring.getTitle();
        this.description = mentoring.getDescription();
        this.duty = mentoring.getDuty();
        this.career = mentoring.getCareer();
        this.price = mentoring.getPrice();
        this.contact = mentoring.getContact();
        this.introductionLink = mentoring.getIntroductionLink();
    }
}
