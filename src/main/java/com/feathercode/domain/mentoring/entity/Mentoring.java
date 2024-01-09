package com.feathercode.domain.mentoring.entity;

import com.feathercode.util.BaseTimeEntity;
import com.feathercode.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mentoring extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String title;
    private String description;
    private String duty;
    private String career;
    private int price;
    private String contact;
    private String introductionLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Mentoring(String title, String description, String duty, String career, int price, String contact, String introductionLink, User user) {
        this.title = title;
        this.description = description;
        this.duty = duty;
        this.career = career;
        this.price = price;
        this.contact = contact;
        this.introductionLink = introductionLink;
        this.user = user;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrice(int price) {this.price = price;}

}