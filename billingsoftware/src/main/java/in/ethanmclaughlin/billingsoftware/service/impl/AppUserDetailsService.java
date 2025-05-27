package in.ethanmclaughlin.billingsoftware.service.impl;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.ethanmclaughlin.billingsoftware.Entity.UserEntity;
import in.ethanmclaughlin.billingsoftware.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{

    private final UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException {
           UserEntity existingUser = UserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email Not Found " + email));
           
           System.out.println("Found user: " + existingUser.getEmail());
           System.out.println("User password in DB: " + existingUser.getPassword());
           System.out.println("User role in DB: " + existingUser.getRole());
    
           return new User(existingUser.getEmail(), existingUser.getPassword(), 
                Collections.singleton(new SimpleGrantedAuthority(existingUser.getRole())));
    }



}
