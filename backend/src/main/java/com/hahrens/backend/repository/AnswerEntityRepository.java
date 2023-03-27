package com.hahrens.backend.repository;

import com.hahrens.backend.model.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Storage repository for {@link AnswerEntity}.
 */
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
}
