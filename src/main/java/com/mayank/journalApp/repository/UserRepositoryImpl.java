package com.mayank.journalApp.repository;

import com.mayank.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSentimentalAnalysis(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        return mongoTemplate.find(query, User.class);
    }
}
