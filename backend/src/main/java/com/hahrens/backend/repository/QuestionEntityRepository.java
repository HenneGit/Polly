package com.hahrens.backend.repository;

import com.hahrens.backend.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Storage Repository for {@link QuestionEntity}.
 */
public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {
}
