package com.translationagency.ordermanager.config;

import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.repository.UserRepository;
import com.translationagency.ordermanager.security.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

    private UserRepository userRepository;

    @Profile({"deploy", "dev", "loginTest"})
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("Authenticating user {}", username);
            Optional<User> user = userRepository.getUserByNameIgnoreCase(username);
            return new AuthorizedUser(user.orElseThrow(() ->
                    new UsernameNotFoundException(String.format("User %s was not found", username))));
        };
    }

    @Profile({"deploy", "dev", "loginTest"})
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Profile({"deploy", "dev", "loginTest"})
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Profile({"deploy", "dev", "loginTest"})
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers("/WEB-INF/jsp/**", "/resources/**", "/login-form/**")
                                .permitAll()
                                .requestMatchers("/users", "/users/**").hasRole(Role.ADMIN.name())
                                .anyRequest().hasRole(Role.USER.name()))
                .formLogin(login -> login.loginPage("/login-form").permitAll()
                        .defaultSuccessUrl("/orders")
                        .failureUrl("/login-form?error=true")
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

    @Profile("test")
    @Bean
    public UserDetailsService userDetailsServiceForTests() {
        UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .authorities(Role.ADMIN, Role.USER)
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Profile("test")
    @Bean
    public SecurityFilterChain filterChainForTests(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers("/WEB-INF/jsp/**", "/resources/**").permitAll()
                                .requestMatchers("/users", "/users/**").hasRole(Role.ADMIN.name())
                                .anyRequest().hasRole(Role.USER.name()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
