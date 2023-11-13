package com.feathercode.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="user_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLike {

    @Id
    @Column(name = "user_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "to_user_id")
    private Long toUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User user;
}