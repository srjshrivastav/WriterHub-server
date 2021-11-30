package com.writerHub.practice.service;


import com.writerHub.practice.models.Author;
import com.writerHub.practice.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author){
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;
    }
}
