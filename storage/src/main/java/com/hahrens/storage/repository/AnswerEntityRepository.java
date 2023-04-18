package com.hahrens.storage.repository;

import com.hahrens.storage.model.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Storage repository for {@link AnswerEntity}.
 */
@Repository
public interface AnswerEntityRepository extends JpaRepository<AnswerEntity, Long> {
}
