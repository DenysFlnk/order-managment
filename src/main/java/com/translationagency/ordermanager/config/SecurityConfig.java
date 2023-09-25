package com.translationagency.ordermanager.config;

import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.repository.UserRepository;
import com.translationagency.ordermanager.security.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("Authenticating user {}", username);
            Optional<User> user = userRepository.getUserByNameIgnoreCase(username);
            return new AuthorizedUser(user.orElseThrow(() ->
                    new UsernameNotFoundException(String.format("User %s was not found", username))));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers("/WEB-INF/jsp/**", "/resources/**").permitAll()
                                .requestMatchers("/users", "/users/**").hasRole(Role.ADMIN.name())
                                .anyRequest().hasRole(Role.USER.name()))
                .formLogin(login -> login.loginPage("/login-form").permitAll()
                        .defaultSuccessUrl("/orders")
                        .loginProcessingUrl("/security-check")
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .logout(logOut -> logOut.deleteCookies("remove")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/login-form"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login-form"))
                .build();
    }
}
