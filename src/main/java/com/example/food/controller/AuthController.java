package com.example.food.controller;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.food.dto.AuthResponseDTO;
import com.example.food.dto.LoginDto;
import com.example.food.dto.RegisterDto;
import com.example.food.entity.Role;
import com.example.food.entity.UserEntity;
import com.example.food.repository.RoleRepository;
import com.example.food.repository.UserEntityRepository;
import com.example.food.security.JWTGenerator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserEntityRepository userEntityRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserEntityRepository userEntityRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userEntityRepository = userEntityRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        try {
            System.out.println("=== Login Debug Info ===");
            System.out.println("Username: " + loginDto.getUsername());
            
            // Kiểm tra user trong database
            UserEntity user = userEntityRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            System.out.println("User found in database");
            System.out.println("User roles: " + user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")));
    
            // Thử authenticate
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
                )
            );
            System.out.println("Authentication successful");
    
            // Generate JWT
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            System.out.println("JWT token generated");
    
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Bad credentials");
            System.out.println("Error details: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new AuthResponseDTO("Invalid username or password"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("Authentication failed with error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new AuthResponseDTO("Authentication failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        // if (userEntityRepository.existByUsername(registerDto.getUsername())) {
        //     return new ResponseEntity<>("User is taken!", HttpStatus.BAD_REQUEST);
        // }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userEntityRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}
