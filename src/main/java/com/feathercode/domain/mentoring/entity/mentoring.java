package com.feathercode.domain.mentoring.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class mentoring extends BaseTimeEntity {
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
}
