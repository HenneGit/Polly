package com.hahrens.controller.implementation.security.config;

import com.hahrens.storage.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig  {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors().and().csrf().disable() //
                .authorizeHttpRequests()//
                .requestMatchers(new AntPathRequestMatcher("/user/registration/register"))//
                .permitAll()//
                .and()//
                .authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/user")).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .and().formLogin().and().build();
    }


}
