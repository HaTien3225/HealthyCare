/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author lehuy
 */
@Controller
public class UserController {
    @GetMapping("/login")
    public String loginView(){
        return "login";
    }
    @PostMapping("/admin/users/create")
    public String createUser(){
        return null;
    }
}
