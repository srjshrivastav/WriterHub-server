package com.writerHub.practice.repo;

import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
