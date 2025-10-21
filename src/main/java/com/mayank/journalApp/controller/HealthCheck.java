package com.mayank.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




// -> Provides @Component and all the endpoints declared in this class, their result will be automatically be converted into json format
//json -> JavaScript object notation - > used to store and transmit data between applications

@RestController
public class HealthCheck {
    @GetMapping("/health-check")
    public String healthCheck(){

        return "ok" ;

    }

}
