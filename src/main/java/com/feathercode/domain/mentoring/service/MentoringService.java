package com.feathercode.domain.mentoring.service;

import com.feathercode.domain.mentoring.controller.dto.MentoringForm;
import com.feathercode.domain.mentoring.entity.Mentoring;
import com.feathercode.domain.mentoring.repository.MentoringRepository;
import com.feathercode.domain.mentoring.service.dto.MentoringDto;
import com.feathercode.domain.user.entity.User;
import com.feathercode.domain.user.repository.UserRepository;
import com.feathercode.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentoringService {

    private final MentoringRepository mentoringRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> save(MentoringForm mentoringform, String email) {

        User user = userRepository.findUserByEmail(email);

        Mentoring mentoring = Mentoring.builder()
                .title(mentoringform.getTitle())
                .description(mentoringform.getDescription())
                .duty(mentoringform.getDuty())
                .career(mentoringform.getCareer())
                .price(mentoringform.getPrice())
                .contact(mentoringform.getContact())
                .introductionLink(mentoringform.getIntroductionLink())
                .user(user)
                .build();

        Mentoring mt = mentoringRepository.save(mentoring);
        MentoringDto mentoringDto = getMentoringDto(mt);

        return Response.makeResponse(HttpStatus.OK, "멘토링 게시글이 작성 되었습니다.", 1, mentoringDto);
    }

    public ResponseEntity<?> delete(Long mentoringId) {
        if (mentoringRepository.findById(mentoringId).orElse(null) == null)
            return Response.badRequest("해당하는 멘토링 글이 없습니다.");

        Mentoring mentoring = mentoringRepository.findMentoringById(mentoringId);
        mentoringRepository.delete(mentoring);

        return Response.makeResponse(HttpStatus.OK, "멘토링 게시글이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findMentoringById(Long mentoringId) {

        if (mentoringRepository.findById(mentoringId).orElse(null) == null)
            return Response.badRequest("해당하는 멘토링 글이 없습니다.");
        Mentoring mentoring = mentoringRepository.findMentoringById(mentoringId);
        MentoringDto mentoringDto = getMentoringDto(mentoring);

        return Response.makeResponse(HttpStatus.OK, "해당 게시글 검색에 성공 하셨습니다.", 1, mentoringDto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<Mentoring> mentoringList = mentoringRepository.findAll();
        List<MentoringDto> mentoringDtoList = mentoringList.stream().map(MentoringDto::new).toList();

        return Response.makeResponse(HttpStatus.OK, "모든 게시글이 검색에 성공 하였습니다.", mentoringDtoList.size(), mentoringDtoList);
    }

    public ResponseEntity<?> updateTitle(Long mentoringId, String title) {
        if (mentoringRepository.findById(mentoringId).orElse(null) == null)
            return Response.badRequest("해당하는 멘토링 게시글이 없습니다.");

        Mentoring mentoring = mentoringRepository.findMentoringById(mentoringId);
        mentoring.updateTitle(title);

        MentoringDto mentoringDto = getMentoringDto(mentoring);

        return Response.makeResponse(HttpStatus.OK, "게시글이 수정되었습니다.", 1, mentoringDto);
    }

    public ResponseEntity<?> updateDescription(Long mentoringId, String description) {
        if (mentoringRepository.findById(mentoringId).orElse(null) == null)
            return Response.badRequest("해당하는 멘토링 게시글이 없습니다.");

        Mentoring mentoring = mentoringRepository.findMentoringById(mentoringId);
        mentoring.updateDescription(description);

        MentoringDto mentoringDto = getMentoringDto(mentoring);

        return Response.makeResponse(HttpStatus.OK, "게시글이 수정되었습니다.", 1, mentoringDto);
    }

    public ResponseEntity<?> updatePrice(Long mentoringId, int price) {
        if (mentoringRepository.findById(mentoringId).orElse(null) == null)
            return Response.badRequest("해당하는 멘토링 글이 없습니다.");

        Mentoring mentoring = mentoringRepository.findMentoringById(mentoringId);
        mentoring.updatePrice(price);

        MentoringDto mentoringDto = getMentoringDto(mentoring);

        return Response.makeResponse(HttpStatus.OK, "게시글이 수정되었습니다.", 1, mentoringDto);
    }

    private MentoringDto getMentoringDto(Mentoring mentoring) {
        return MentoringDto.builder()
                .title(mentoring.getTitle())
                .description(mentoring.getDescription())
                .duty(mentoring.getDuty())
                .career(mentoring.getCareer())
                .price(mentoring.getPrice())
                .contact(mentoring.getContact())
                .introductionLink(mentoring.getIntroductionLink())
                .build();
    }
}
