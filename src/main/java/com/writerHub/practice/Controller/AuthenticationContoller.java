package com.writerHub.practice.Controller;

import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationContoller {

    @Autowired
    private WriterHubUserService writerHubUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity<List<Author>> login(){
        //return ResponseEntity.ok().body(writerHubUserService());
        return  null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody WriterHubUser writerHubUser) throws AuthorExists {
        writerHubUser.setPassword(passwordEncoder.encode(writerHubUser.getPassword()));
        return writerHubUserService.addUser(writerHubUser);
    }
}
