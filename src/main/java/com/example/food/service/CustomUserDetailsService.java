package com.example.food.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.food.entity.Role;
import com.example.food.entity.UserEntity;
import com.example.food.repository.UserEntityRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @SuppressWarnings("FieldMayBeFinal")
    private UserEntityRepository userEntityRepository;

    @Autowired
    public CustomUserDetailsService(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }


    // @Override
    // public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException{
    //     UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    //     return new User(userEntity.getUsername(), userEntity.getPassword(), mapRolesToAuthorities(userEntity.getRoles()));

    // }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("Loading user: " + username);
            UserEntity user = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                
            System.out.println("Found user: " + user.getUsername());
            System.out.println("Password hash: " + user.getPassword());
            System.out.println("Roles: " + user.getRoles());
    
            return new User(user.getUsername(), 
                           user.getPassword(),
                           user.getRoles().stream()
                               .map(role -> new SimpleGrantedAuthority(role.getName()))
                               .collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error loading user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
}
