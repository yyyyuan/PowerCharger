package com.example.yuanbo.powercharger.model;

import java.util.Date;
import java.util.List;

/**
 * Created by lezhu on 11/29/2017.
 */

public class User {
    private String name;
    private String email;
    private String id;
    private String lastLoginDate;

    public User(String name, String email, String id, String lastLoginDate) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.lastLoginDate = lastLoginDate;
    }

    public User() {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
