package com.writerHub.practice.models;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Company {
    @Id
    private Long id;

    @NotNull
    private  String name;

    @NotNull
    private String address;

    @NotNull @OneToOne(fetch = FetchType.LAZY)
    private WriterHubUser user;

    public Company(){}

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WriterHubUser getUser() {
        return user;
    }

    public void setUser(WriterHubUser user) {
        this.user = user;
    }
}
