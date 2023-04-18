package com.hahrens.storage.repository;


import com.hahrens.storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserEntityRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
