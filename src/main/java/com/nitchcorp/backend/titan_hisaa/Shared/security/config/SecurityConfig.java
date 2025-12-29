package com.nitchcorp.backend.titan_hisaa.Shared.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nitchcorp.backend.titan_hisaa.Shared.security.constants.JavaConstant;
import com.nitchcorp.backend.titan_hisaa.Shared.security.jwt.filters.JwtAuthorizationToken;
import com.nitchcorp.backend.titan_hisaa.Shared.security.userDetailsConf.UserServiceSecure;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private JwtAuthorizationToken authenticationFilter;

    public SecurityConfig(JwtAuthorizationToken authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                //.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.authenticationProvider(authenticationProvider());
                .authorizeHttpRequests(auth -> auth
                        // URLs publiques (sans auth)
                        .requestMatchers(JavaConstant.PUBLIC_URLS).permitAll()
                        // URLs TITAN ADMIN 
                        .requestMatchers(JavaConstant.TITAN_ADMIN_URLS).hasAnyRole("TITAN_ADMIN")
                        // URLs SOCIETY ADMIN AND GENERAL CASH ADMIN
                        .requestMatchers(JavaConstant.SOCIETY_ADMIN_AND_GENERAL_CASH_ADMIN_URLS).hasAnyRole("SOCIETY_ADMIN", "GENERAL_CASH_ADMIN")
                        //URLs SOCIETY ADMIN
                        .requestMatchers(JavaConstant.SOCIETY_ADMIN_URLS).hasRole("SOCIETY_ADMIN")
                        //URLs GENERAL CASH ADMIN
                        .requestMatchers(JavaConstant.GENERAL_CASH_ADMIN_URLS).hasRole("GENERAL_CASH_ADMIN")
                        //URLs STORE ADMIN
                        .requestMatchers(JavaConstant.STORE_ADMIN_URLS).hasRole("STORE_ADMIN")
                        //URLs CASHIER
                        .requestMatchers(JavaConstant.CASHIER_URLS).hasRole("CASHIER")
                        //URLs CUSTUMER
                        .requestMatchers(JavaConstant.CUSTUMER_URLS).hasRole("CUSTUMER")
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserServiceSecure userServiceSecure) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userServiceSecure);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Configuration CORS spécifique pour production et développement
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*",
                "https://astonishing-medovik-c97d49.netlify.app",
                "https://*.netlify.app",
                "https://titan-backend-springboot-new-*.onrender.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token", "authorization", "content-type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache preflight pour 1 heure

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
