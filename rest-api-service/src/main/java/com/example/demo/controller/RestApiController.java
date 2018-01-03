package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ksb on 2018. 1. 2..
 */
@RestController
public class RestApiController {

    @RequestMapping(method = RequestMethod.GET, value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value="/get")
    public String get(String param) {
        return "get" + param;
    }

    @RequestMapping(method = RequestMethod.POST, value="/post")
    public String post(String param) {
        return "post" + param;
    }

    @RequestMapping(method = RequestMethod.POST, value="/migration")
    public String migration(String serviceName, boolean reset){
        return "migration" + serviceName;
    }

    @RequestMapping(method = RequestMethod.POST, value="/migration/multiple")
    public String migration(String[] multipleServiceName, boolean reset) {
        return "migration" + String.join(",", multipleServiceName);
    }

}
