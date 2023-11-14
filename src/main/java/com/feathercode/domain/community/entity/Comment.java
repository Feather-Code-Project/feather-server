package com.feathercode.domain.community.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.user.domain.User;
import jakarta.persistence.*;

@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String contents;
    private int likesCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
