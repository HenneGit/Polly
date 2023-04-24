package com.hahrens.controller.service.security;

import com.hahrens.controller.implementation.registration.RegistrationRequest;
import com.hahrens.controller.implementation.registration.exception.UserAlreadyExistsException;
import com.hahrens.controller.implementation.registration.validation.TokenValidationResult;
import com.hahrens.controller.implementation.service.security.RegistrationServiceImpl;
import com.hahrens.storage.model.User;
import com.hahrens.storage.model.UserRole;
import com.hahrens.storage.model.VerificationToken;
import com.hahrens.storage.repository.UserEntityRepository;
import com.hahrens.storage.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserEntityRepository userEntityRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    private static final String EMAIL = "diesDas";
    private User user;
    private VerificationToken verificationToken;
    private static final String PASSWORD = "asoöifdh-asDLÖKHSDFKJH-AFNNAS";
    private static final String TOKEN = UUID.randomUUID().toString();
    private static final Long USER_ID = Long.valueOf(6);
    private static final Long TOKEN_ID = Long.valueOf(5);


    @InjectMocks
    private RegistrationServiceImpl registrationService;


    @BeforeEach
    public void init() {
        user = new User("firstName", "lastName", EMAIL, EMAIL, PASSWORD, UserRole.USER, false, false);
        user.setId(USER_ID);
        verificationToken = new VerificationToken(TOKEN, user);
        verificationToken.setId(TOKEN_ID);
        lenient().when(userEntityRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        lenient().when(userEntityRepository.save(any(User.class))).thenReturn(user);
        lenient().when(passwordEncoder.encode(any(String.class))).thenReturn(PASSWORD);
        lenient().when(verificationTokenRepository.save(any(VerificationToken.class))).thenReturn(verificationToken);
        lenient().when(verificationTokenRepository.findByToken(any(String.class))).thenReturn(verificationToken);

    }

    @Test
    public void testRegisterUser() {
        User registeredUser = registrationService.registerUser(new RegistrationRequest("firstName", "lastName", EMAIL, "password", UserRole.USER));
        assertNotNull(registeredUser);
        assertEquals(registeredUser.getEmail(), EMAIL);
        assertEquals(registeredUser.getPassword(), PASSWORD);
    }

    @Test
    public void testValidateToken() {
        String token = UUID.randomUUID().toString();
        TokenValidationResult tokenValidationResult = registrationService.validateToken(token);
        assertEquals(TokenValidationResult.VALID, tokenValidationResult);
        user.setEnabled(true);
        TokenValidationResult userEnabledToken = registrationService.validateToken(token);
        assertEquals(TokenValidationResult.USER_ENABLED, userEnabledToken);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, -16);
        Date time = calendar.getTime();
        verificationToken.setExpirationDate(time);
        user.setEnabled(false);
        TokenValidationResult expiredTokenResult = registrationService.validateToken(token);
        assertEquals(TokenValidationResult.EXPIRED, expiredTokenResult);


    }




}
