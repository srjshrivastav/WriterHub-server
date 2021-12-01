package com.writerHub.practice.repo;

import com.writerHub.practice.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
