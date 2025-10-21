package com.mayank.journalApp.service;

import com.mayank.journalApp.entity.JournalEntry;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.repository.JournalEntryRepository;
import com.mayank.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
        userRepository.save(user);
    }


    public List<User> getAllEntries(){
        return userRepository.findAll();
    }



    public Optional<User> getEntryById(ObjectId id){

        return userRepository.findById(id);
    }



    public void deleteEntryById(ObjectId id){
        userRepository.deleteById(id);

    }

    public User findByUserName(String userName){
        return userRepository.findByuserName(userName);
    }



}


