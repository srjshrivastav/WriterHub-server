package com.writerHub.practice.service;

import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            return new User(user.getUsername(), user.getPassword(), null);
        }

    }

    public ResponseEntity<?> addUser(WriterHubUser user) throws AuthorExists{
       WriterHubUser savedUser =  userRepository.save(user);
       if(savedUser != null){
           return ResponseEntity.ok().body("Sucess");
       }
       else{
           String subject = user.isCompany()?"Company":"Author";
           throw new AuthorExists(subject+" Already exists!");
       }
    }

}
