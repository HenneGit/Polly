package com.hahrens.backend.repository;

import com.hahrens.backend.model.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyEntityRepository extends JpaRepository<SurveyEntity, Long> {
}
