package com.writerHub.practice.models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

enum AuthorType{
    INDIVIDUAL,
    ORGANIZATION
}

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    private AuthorType type;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> employees;

    public Author(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AuthorType getType() {
        return type;
    }

    public void setType(AuthorType type) {
        this.type = type;
    }

    public List<Author> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Author> employees) {
        this.employees = employees;
    }
}
