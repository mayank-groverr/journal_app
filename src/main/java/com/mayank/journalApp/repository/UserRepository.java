package com.mayank.journalApp.repository;


import com.mayank.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{ 
    User findByuserName(String userName);
    void deleteByuserName(String userName);
}