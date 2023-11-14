package com.feathercode.domain.community.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import com.feathercode.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;
    private String title;
    private String content;
    private int likeCnt;
    private int commentCnt;
    private int hits;
    private String communityImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
