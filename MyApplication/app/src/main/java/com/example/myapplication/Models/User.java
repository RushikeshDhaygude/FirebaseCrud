package com.example.myapplication.Models;

import java.io.Serializable;

import com.google.firebase.firestore.Exclude;
public class User implements Serializable {



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    private String id;


    private String name, address, phone;

    public User() {

    }

    public User(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}