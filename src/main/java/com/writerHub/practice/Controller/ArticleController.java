package com.writerHub.practice.Controller;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.writerHub.practice.Util.JWTUtil;
import com.writerHub.practice.models.*;
import com.writerHub.practice.service.ArticleService;
import com.writerHub.practice.service.AuthorService;
import com.writerHub.practice.service.CompanyService;
import com.writerHub.practice.service.WriterHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WriterHubUserService writerHubUserService;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        List<Article> articleList = articleService.getAllArticles();
        return  ResponseEntity.ok().body(articleList);
    }

    @GetMapping("/author/{authorId}/articles")
    public ResponseEntity<?> getAllArticlesByAuthor(@PathVariable Long authorId){
        List<Article> articleList = articleService.getArticleByAuthorId(authorId);
        return  ResponseEntity.ok().body(articleList);
    }

    @PostMapping("/author/{authorId}/article")
    public ResponseEntity<?> saveArticle(@RequestBody Article article,@PathVariable Long authorId){
        AuthenticationError error = new AuthenticationError();
        try {
            Author author = new Author(authorId);
            article.setAuthor(author);
            Article savedArticle = articleService.saveArticle(article);
            return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
        }
        catch (Exception exception){
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            error.setMessage(exception.getMessage());
        }
        return new ResponseEntity<>(error,error.getStatusCode());
    }

    @DeleteMapping("author/{authorId}/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id,@PathVariable Long authorId){
        Article article = articleService.getArticle(id);
        if(article != null && article.getAuthor().getId() == authorId){
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        }
        else{
            AuthenticationError error = new AuthenticationError();
            error.setStatusCode(HttpStatus.UNAUTHORIZED);
            error.setMessage("This article is not belongs to you.");
            return new ResponseEntity<>(error,error.getStatusCode());
        }
    }

    @GetMapping("author/{authorId}/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id){
        Article article = articleService.getArticle(id);
        return ResponseEntity.ok().body(article);
    }

}
