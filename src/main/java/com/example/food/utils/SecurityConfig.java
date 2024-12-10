package com.example.food.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.food.security.JWTAuthenticationFilter;
import com.example.food.security.JWTGenerator;
import com.example.food.security.JwtAuthEntryPoint;
import com.example.food.service.CustomUserDetailsService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint authEntryPoint;
    
    private CustomUserDetailsService userDetailsService;   
    private JWTGenerator jwtGenerator;

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService; // Ensure CustomUserDetailsService is used
    } 

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint, JWTGenerator jwtGenerator){
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtGenerator = jwtGenerator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable();
                
        http.addFilterBefore(jWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    

    


    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //         .userDetailsService(userDetailsService) // Use the CustomUserDetailsService
    //         .passwordEncoder(passwordEncoder()) // Ensure password encoder is set
    //         .and()
    //         .build();
    // }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authBuilder.userDetailsService(userDetailsService)
                  .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jWTAuthenticationFilter() {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        filter.setTokenGenerator(jwtGenerator);
        filter.setCustomUserDetailsService(userDetailsService);
        return filter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

     
}
