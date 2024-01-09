//package com.feathercode.config;
//
//import com.feathercode.domain.user.entity.User;
//import com.feathercode.domain.model.Gender;
//import com.feathercode.domain.user.repository.UserRecommendationRepository;
//import com.feathercode.domain.user.repository.UserRepository;
//import com.feathercode.domain.user.service.UserRecommendationService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class initDb {
//    private final UserInitService userInitService;
//    private final UserRecommendationInitService userRecommendationInitService;
//
//    @PostConstruct
//    public void init() {
//        userInitService.dbInit(); // 유저 초기화
//        userRecommendationInitService.dbInit(); // 유저 추천 초기화
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class UserInitService {
//        private final UserRepository userRepository;
//
//        public void dbInit() {
//            User user1 = User.builder()
//                    .username("user1")
//                    .nickname("User One")
//                    .email("user1@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.MAN)
//                    .build();
//
//            User user2 = User.builder()
//                    .username("user2")
//                    .nickname("User Two")
//                    .email("user2@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.WOMAN)
//                    .build();
//
//            User user3 = User.builder()
//                    .username("user3")
//                    .nickname("User Three")
//                    .email("user3@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.WOMAN)
//                    .build();
//
//            User user4 = User.builder()
//                    .username("user4")
//                    .nickname("User Four")
//                    .email("user4@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.MAN)
//                    .build();
//
//            User user5 = User.builder()
//                    .username("user5")
//                    .nickname("User Five")
//                    .email("user5@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.MAN)
//                    .build();
//
//            User user6 = User.builder()
//                    .username("user6")
//                    .nickname("User Six")
//                    .email("user6@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.WOMAN)
//                    .build();
//
//            User user7 = User.builder()
//                    .username("user7")
//                    .nickname("User Seven")
//                    .email("user7@example.com")
//                    .likesCnt(0)
//                    .recommendationsCnt(0)
//                    .gender(Gender.MAN)
//                    .build();
//            userRepository.save(user1);
//            userRepository.save(user2);
//            userRepository.save(user3);
//            userRepository.save(user4);
//            userRepository.save(user5);
//            userRepository.save(user6);
//            userRepository.save(user7);
//        }
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class UserRecommendationInitService {
//        private final UserRepository userRepository;
//        private final UserRecommendationRepository userRecommendationRepository;
//        private final UserRecommendationService userRecommendationService;
//
//
//        public void dbInit() {
//            List<User> userList = userRepository.findAll();
//
//            int j=0;
//            for (int i = 0; i < 100; i++,j++) {
//                User fromUser = userList.get(i % userList.size()); // Cyclically select users as fromUser
//                User toUser = userList.get((i * j) % userList.size()); // Cyclically select users as toUser
//
//                if (!fromUser.getId().equals(toUser.getId())) {
//                    if (!userRecommendationRepository.existsByToUserAndFromUser(toUser, fromUser)) {
//                        userRecommendationService.save(toUser.getId(),fromUser.getId());
//                    }
//                }
//            }
//        }
//    }
//}
