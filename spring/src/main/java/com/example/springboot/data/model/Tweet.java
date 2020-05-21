package com.example.springboot.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;
    private String text;

    public Tweet() {
    }

    public Tweet(String userName, String text) {
        this.userName = userName;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setFirstName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}