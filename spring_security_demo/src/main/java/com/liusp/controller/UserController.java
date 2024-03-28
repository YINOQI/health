package com.liusp.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @GetMapping(value = "/hello")
    public ModelAndView hello(){
        return new ModelAndView("/login.html");
    }

    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/index.html");
    }

    @GetMapping(value = "/403")
    public ModelAndView go403(){
        return new ModelAndView("/403.html");
    }

    @GetMapping("/test")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public String test() {
        return "hello";
    }

    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('ROLE_USER')")
    public String test2() {
        return "hello2";
    }
}