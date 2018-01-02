package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ksb on 2018. 1. 2..
 */
@RestController
public class RestApiController {

    @RequestMapping(method = RequestMethod.GET, value="/test")
    public String test(){
        return "test";
    }

}
