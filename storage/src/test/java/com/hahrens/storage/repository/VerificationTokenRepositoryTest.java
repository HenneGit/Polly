package com.hahrens.storage.repository;

import com.hahrens.storage.model.User;
import com.hahrens.storage.model.UserRole;
import com.hahrens.storage.model.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VerificationTokenRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private VerificationTokenRepository repository;
    private static final String FIRST_NAME = "David";
    private static final String LAST_NAME = "Hilbert";
    private static final String EMAIL = "hilbertshotel@gmail.com";
    private static final String TOKEN = UUID.randomUUID().toString();

    @BeforeEach
    public void init() {
        User user = new User(FIRST_NAME, LAST_NAME, EMAIL, EMAIL, UUID.randomUUID().toString(), UserRole.USER, false, true);
        userEntityRepository.save(user);
        VerificationToken verificationToken = new VerificationToken(TOKEN, user);
        repository.save(verificationToken);
    }

    @Test
    public void testFindToken() {
        VerificationToken byToken = repository.findByToken(TOKEN);
        assertNotNull(byToken);
        assertEquals(byToken.getToken(), TOKEN);
        assertEquals(byToken.getUser().getEmail(), EMAIL);
    }

    @Test
    public void testExpirationDate() {
        VerificationToken byToken = repository.findByToken(TOKEN);
        Date expirationDate = byToken.getExpirationDate();
        assertNotNull(expirationDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, 16);
        Date time = calendar.getTime();
        assertTrue(time.after(expirationDate));
    }



}
