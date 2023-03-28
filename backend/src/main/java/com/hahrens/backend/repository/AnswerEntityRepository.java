package com.hahrens.backend.repository;

import com.hahrens.backend.model.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Storage repository for {@link AnswerEntity}.
 */
@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
}
