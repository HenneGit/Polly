package com.hahrens.backend.repository;

import com.hahrens.backend.model.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Storage repository for {@link SurveyEntity}.
 */
@Repository
public interface SurveyEntityRepository extends JpaRepository<SurveyEntity, Long> {
}
