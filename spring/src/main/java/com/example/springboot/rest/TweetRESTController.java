package com.example.springboot.rest;

import com.example.springboot.data.TweetRepository;
import com.example.springboot.data.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TweetRESTController
{
    @Autowired
    private TweetRepository repository;

    public TweetRepository getRepository() {
        return repository;
    }

    public void setRepository(TweetRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tweets")
    public List<Tweet> getAllEmployees() {
        return repository.findAll();
    }
}