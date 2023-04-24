package com.hahrens.storage.repository;

import com.hahrens.storage.model.User;
import com.hahrens.storage.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;
    private static final String FIRST_NAME = "David";
    private static final String LAST_NAME = "Hilbert";
    private static final String EMAIL = "hilbertshotel@gmail.com";


    @BeforeEach
    public void init() {
        User user = new User(FIRST_NAME, LAST_NAME, EMAIL, EMAIL, UUID.randomUUID().toString(), UserRole.USER, false, true);
        userEntityRepository.save(user);
    }

    @Test
    public void testFindUser() {
        Optional<User> byEmail = userEntityRepository.findByEmail(EMAIL);
        assertTrue(byEmail.isPresent());
        User user = byEmail.get();
        assertEquals(user.getFirstName(), FIRST_NAME);
        assertEquals(user.getUserRole(), UserRole.USER);
        assertEquals(user.getLastName(), LAST_NAME);
        assertEquals(user.getUsername(), EMAIL);
        assertEquals(user.getEmail(), EMAIL);

    }

}
