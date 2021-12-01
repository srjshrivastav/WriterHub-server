package com.writerHub.practice.Controller;

import com.writerHub.practice.models.Author;
import com.writerHub.practice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors(){
        List<Author> authorList = authorService.getAllAuthors();
        return  ResponseEntity.ok().body(authorList);
    }

    @PostMapping("/author")
    public ResponseEntity<?> saveArticle(HttpServletRequest request, @RequestBody Author article){
        Author savedAuthor = authorService.saveAuthor(article);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id){
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok().body(author);
    }
}
