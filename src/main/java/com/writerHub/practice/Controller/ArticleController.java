package com.writerHub.practice.Controller;


import com.writerHub.practice.models.Article;
import com.writerHub.practice.service.ArticleService;
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

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        List<Article> articleList = articleService.getAllArticles();
        return  ResponseEntity.ok().body(articleList);
    }

    @PostMapping("/article")
    public ResponseEntity<?> saveArticle(HttpServletRequest request, @RequestBody Article article){
        Article savedArticle = articleService.saveArticle(article);
        return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id){
        Article article = articleService.getArticle(id);
        return ResponseEntity.ok().body(article);
    }

}
