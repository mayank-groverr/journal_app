package com.mayank.journalApp.service;


import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {


    private  static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userRepository.save(user);
    }

    public void saveUser(User user){
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

    public void deleteByUserName(String name){
        userRepository.deleteByuserName(name);
    }

    public User findByUserName(String userName){
        return userRepository.findByuserName(userName);
    }

}


