package com.feathercode.domain.codereview.entity;

import com.feathercode.util.BaseTimeEntity;
import com.feathercode.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String title;
    private String code;
    private String description;
    private int recommendationsCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
