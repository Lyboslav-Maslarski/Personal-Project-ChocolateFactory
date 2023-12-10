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
                                .requestMatchers(HttpMethod.GET, "api/users/all")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "api/users/{id}")
                                .authenticated()
                                .requestMatchers(HttpMethod.PATCH, "api/users/{id}", "api/users/{id}/password")
                                .authenticated()


                                .requestMatchers(HttpMethod.GET, "api/products/all", "api/products/{id}")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "api/products/add")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.PATCH, "api/products/update/")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.GET, "api/products/update/{id}")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.PATCH, "api/products/{id}")
                                .hasRole("MODERATOR")

                                .requestMatchers(HttpMethod.POST, "api/comments/add")
                                .authenticated()
                                .requestMatchers(HttpMethod.DELETE, "api/comments/{id}")
                                .authenticated()

                                .requestMatchers(HttpMethod.GET, "api/orders/all")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.PATCH, "api/orders/accept", "api/orders/dispatch")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.POST, "api/orders/add")
                                .authenticated()
                                .requestMatchers(HttpMethod.GET, "api/orders/{orderNumber}")
                                .authenticated()
                                .requestMatchers(HttpMethod.DELETE, "api/orders/{id}")
                                .authenticated()

                                .requestMatchers(HttpMethod.POST, "api/messages/add")
                                .authenticated()
                                .requestMatchers(HttpMethod.PATCH, "api/messages/{id}")
                                .hasRole("MODERATOR")
                                .requestMatchers(HttpMethod.GET, "api/messages/all")
                                .hasRole("MODERATOR")

                                .anyRequest().authenticated());

        return http.build();
    }
}
