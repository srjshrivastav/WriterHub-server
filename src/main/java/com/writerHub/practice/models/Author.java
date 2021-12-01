package com.writerHub.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Author {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date dob;

    @NotNull
    private String phone;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    @JsonIgnore
    private WriterHubUser user;

    public Author(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public WriterHubUser getUser() {
        return user;
    }

    public void setUser(WriterHubUser user) {
        this.user = user;
    }

}
