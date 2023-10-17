package com.example.chocolatefactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthProvider userAuthProvider;

    public SecurityConfig(UserAuthProvider userAuthProvider) {
        this.userAuthProvider = userAuthProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(HttpMethod.POST, "api/users/login", "api/users/register")
                                .permitAll()
                                .requestMatchers(HttpMethod.DELETE, "api/users/{id}")
                                .authenticated()
                                .requestMatchers(HttpMethod.PATCH, "api/users/{id}","api/users/{id}/password")
                                .authenticated()
                                .requestMatchers(HttpMethod.GET, "api/products/all", "api/products/{id}")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "api/comments/add")
                                .authenticated()
                                .requestMatchers(HttpMethod.POST, "api/orders/add")
                                .authenticated()
                                .anyRequest().authenticated());

        return http.build();
    }
}
