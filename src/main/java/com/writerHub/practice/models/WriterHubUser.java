package com.writerHub.practice.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "user")
public class WriterHubUser{

    @Id @GeneratedValue(strategy = AUTO)
    private Long userId;
    @NotNull @Column(name = "username",unique = true)
    private String username;
    @NotNull
    private String password;
    private boolean isCompany;

    public WriterHubUser(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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
