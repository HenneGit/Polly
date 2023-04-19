package com.hahrens.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "tb_verification_token")
@Setter
@Getter
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationDate;

    @OneToMany()
    @JoinColumn(name ="user_id")
    private User user;

    private static final int EXPIRATION_TIME = 15;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationDate = this.getExpirationDate();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationDate = this.getExpirationDate();
    }

    private Date getTokenExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return calendar.getTime();
    }


}
