package com.feathercode.domain.mentoring.repository;

import com.feathercode.domain.mentoring.entity.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring,Long> {
    Mentoring findMentoringById(Long mentoringId);
}
