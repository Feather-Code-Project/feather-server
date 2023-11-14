package com.feathercode.domain.codereview.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_review_id")
    private Long id;

    private String title;
    private String code;
    private String description;
    private int likesCnt;
    private int repliesCnt;
    private int hitsCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
