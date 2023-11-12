package com.feathercode.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRecommendations {

    @Id
    @Column(name = "user_recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "to_user_id")
    private Long toUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "from_user_id")
    private User user;
}