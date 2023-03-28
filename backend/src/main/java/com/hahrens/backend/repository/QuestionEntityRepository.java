package com.hahrens.backend.repository;

import com.hahrens.backend.model.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Storage Repository for {@link QuestionEntity}.
 */

@Repository
public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {
}
