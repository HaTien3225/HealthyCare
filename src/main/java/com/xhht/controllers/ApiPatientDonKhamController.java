/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.services.DonKhamService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiPatientDonKhamController {
    
    @Autowired
    private DonKhamService donKhamService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/donkham")
    public ResponseEntity<?> listDonKham(@RequestParam (name = "kw",required = false)String kw,
            @RequestParam (name = "page",required = false)Integer page,
            @RequestParam (name = "pageSize",required = false)Integer pageSize,
            @RequestParam (name = "date",required = false)LocalDate date,
            Principal principal){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 10;
        return ResponseEntity.ok(this.donKhamService.getAllDonKham(u.getId(), true, page, pageSize, kw, date));
    }
    
}
