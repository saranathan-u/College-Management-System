package com.example.college_management.config;

import com.example.college_management.service.CustomUserDetailsService;
import com.example.college_management.service.FacultyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private FacultyDetailsService facultyDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //Faculty Authentication Provider
    @Bean
    public DaoAuthenticationProvider facultyAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(facultyDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //Student Authentication Provider
    @Bean
    public DaoAuthenticationProvider studentAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //Combined Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of(
                            "http://127.0.0.1:5500",
                            "http://localhost:5500",
                            "http://localhost:3000"
                    ));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(studentAuthProvider())
                .authenticationProvider(facultyAuthProvider())
                .authorizeHttpRequests(auth -> auth
                        //Public endpoints (login, register, docs, uploads)
                        .requestMatchers(
                                "/api/auth/login",      // 🔥 Added this
                                "/api/auth/register",   // 🔥 Added this
                                "/api/students/register",
                                "/api/students/login",
                                "/api/faculty/register",
                                "/api/faculty/login",
                                "/uploads/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        //Faculty endpoints
                        .requestMatchers("/api/faculty/**").hasAuthority("ROLE_FACULTY")
                        .requestMatchers("/api/students/me").authenticated()
                        .requestMatchers("/api/students/**").hasAuthority("ROLE_STUDENT")
                        //Everything else
                        .anyRequest().authenticated()
                )
                //Add JWT filter before username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Unauthorized or invalid token");
                        })
                );
        return http.build();
    }

    @Bean(name = "facultyAuthManager")
    public AuthenticationManager facultyAuthManager() {

        return new ProviderManager(facultyAuthProvider());
    }

    @Bean(name = "studentAuthManager")
    public AuthenticationManager studentAuthManager() {

        return new ProviderManager(studentAuthProvider());
    }

    //Default Auth Manager
    @Bean
    @Primary
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
