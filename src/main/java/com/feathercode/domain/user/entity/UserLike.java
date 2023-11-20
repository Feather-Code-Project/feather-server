package com.feathercode.domain.user.entity;

import com.feathercode.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="user_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLike extends BaseTimeEntity {

    @Id
    @Column(name = "user_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @Builder
    public UserLike(User toUser,User fromUser){
        this.toUser=toUser;
        this.fromUser=fromUser;
    }
}