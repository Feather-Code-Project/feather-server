package com.feathercode.domain.codereview.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class ReplyRecommendation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_recommendation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
