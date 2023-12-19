package com.example.labb2_spring_boot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

//@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .sessionManagement(ma -> ma.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/Error").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/categories/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/categories").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "api/locations").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/locations").authenticated()
                        .requestMatchers(HttpMethod.GET, "api/locations/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/locations/category/**").authenticated()
                        .anyRequest().denyAll())
                .build();
    }

    @Bean
    public UserDetailsService users(PasswordEncoder encoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
