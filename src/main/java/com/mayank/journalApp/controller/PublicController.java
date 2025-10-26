package com.mayank.journalApp.controller;

import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


// -> Provides @Component and all the endpoints declared in this class, their result will be automatically be converted into json format
//json -> JavaScript object notation - > used to store and transmit data between applications

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){

        return "ok" ;

    }

    @PostMapping("/create-user")
    public void addNewUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

}
