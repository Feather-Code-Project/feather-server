package com.feathercode.domain.user.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="user_recommendation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRecommendation extends BaseTimeEntity {

    @Id
    @Column(name = "user_recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @Builder
    public UserRecommendation(User toUser,User fromUser){
        this.toUser=toUser;
        this.fromUser=fromUser;
    }
}