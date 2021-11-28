package com.board.board.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @ResponseBody
    @RequestMapping(value = "/test")
    public String testController(){
        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "/l7check")
    public String l7check(){
        return "OK";
    }



}
