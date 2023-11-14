package com.feathercode.domain.user.entity;

import com.feathercode.domain.model.Gender;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void init() {
        queryFactory = new JPAQueryFactory(testEntityManager.getEntityManager());

        User user1 = User.builder()
                .username("user1")
                .nickname("nickname1")
                .email("user1@example.com")
                .likesCnt(5)
                .recommendationsCnt(10)
                .gender(Gender.MAN)
                .build();

        User user2 = User.builder()
                .username("user2")
                .nickname("nickname2")
                .email("user2@example.com")
                .likesCnt(8)
                .recommendationsCnt(15)
                .gender(Gender.WOMAN)
                .build();

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
    }

    @Test
    void shouldFindUserByUsername() {
        QUser qUser = QUser.user;

        User foundUser = queryFactory
                .selectFrom(qUser)
                .where(qUser.username.eq("user1"))
                .fetchOne();

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("user1");
        assertThat(foundUser.getNickname()).isEqualTo("nickname1");
        assertThat(foundUser.getEmail()).isEqualTo("user1@example.com");
        // Add more assertions as needed
    }
}
