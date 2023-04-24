package com.hahrens.storage.repository;

import com.hahrens.storage.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    /**
     * find verification token by string token.
     * @param token the string token to find storage object for.
     * @return the found verification token.
     */
    VerificationToken findByToken(String token);

}
