package com.writerHub.practice.service;

import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;

@Service
public class WriterHubUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WriterHubUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found with " + username + " email.");
        } else {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), authorities);
        }

    }

    public ResponseEntity<?> addUser(WriterHubUser user){
        try{
            WriterHubUser savedUser = (WriterHubUser) userRepository.save(user);
            return ResponseEntity.ok().body(savedUser);
        }
        catch (Exception ex){
            AuthenticationError error = new AuthenticationError();
            error.setMessage(user.getUsername()+ " is Already Exists!");
            error.setStatusCode(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(error);
        }
    }

}
