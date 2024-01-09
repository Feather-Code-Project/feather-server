package com.feathercode.domain.user.service;

import com.feathercode.domain.model.Gender;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.entity.UserRecommendation;
import com.feathercode.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserRecommendationServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRecommendationService userRecommendationService;

    @Test
    @Transactional
    void save() {
        User user1 = User.builder()
                .username("강정수")
                .nickname("강강강")
                .email("강정수@naver.com")
                .contents("안녕")
                .userImage("안녕")
                .githubLink("안녕")
                .notionLink("안녕")
                .likesCnt(0)
                .recommendationsCnt(0)
                .gender(Gender.MAN)
                .build();
        User user2 = User.builder()
                .username("이승헌")
                .nickname("이이이")
                .email("이승헌@naver.com")
                .contents("안녕")
                .userImage("안녕")
                .githubLink("안녕")
                .notionLink("안녕")
                .likesCnt(0)
                .recommendationsCnt(0)
                .gender(Gender.WOMAN)
                .build();

        User toUser = userRepository.save(user1);
        User fromUser = userRepository.save(user2);
        assertThat(toUser).isNotNull();
        assertThat(fromUser).isNotNull();
        assertThat(toUser.getRecommendationsCnt()).isEqualTo(0);
        UserRecommendation recommendation = userRecommendationService.save(toUser.getId(), fromUser.getId());
        assertThat(recommendation).isNotNull();
        em.flush();
        em.clear();

        User updatedUserAfterCommit = userRepository.findById(toUser.getId()).orElse(null);

        assertThat(updatedUserAfterCommit).isNotNull();
        assertThat(updatedUserAfterCommit.getRecommendationsCnt()).isEqualTo(1);
    }

}