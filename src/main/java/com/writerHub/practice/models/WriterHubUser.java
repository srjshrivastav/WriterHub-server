package com.writerHub.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "user")
public class WriterHubUser{

    @Id @GeneratedValue(strategy = AUTO)
    private Long userId;
    @Column(name = "username",unique = true,nullable = false)
    private String username;
    private String password;
    private boolean isCompany;
    private String role = "USER";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public WriterHubUser(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
