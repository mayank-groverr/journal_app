package com.mayank.journalApp.controller;


import com.mayank.journalApp.api.response.WeatherResponse;
import com.mayank.journalApp.entity.User;
import com.mayank.journalApp.service.UserService;
import com.mayank.journalApp.service.WeatherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



//Controller -> special type of classes / components for handling http request
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherServices weatherServices;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User userInDb = userService.findByUserName(name);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<String> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response = weatherServices.getWeather("Mumbai");
        String greetings = "";
        if(response != null){
            greetings = String.format("Hi %s weather feels like %d",authentication.getName(),response.getCurrent().getFeelslike());
        }
        return new ResponseEntity<>(greetings,HttpStatus.OK);
    }
}
