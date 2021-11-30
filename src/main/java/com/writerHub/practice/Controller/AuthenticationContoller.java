package com.writerHub.practice.Controller;


import com.writerHub.practice.models.Author;
import com.writerHub.practice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthenticationContoller {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author){
        return ResponseEntity.ok().body(authorService.addAuthor(author));
    }
}
