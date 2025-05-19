package com.xhht.controllers;


import com.xhht.pojo.Benh;
import com.xhht.pojo.User;
import com.xhht.services.BenhService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lehuy
 */
@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorBenhController {
    @Autowired
    private BenhService benhService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/benh")
    public ResponseEntity<?> listBenh(Principal principal,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize){
         if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 100;
        return ResponseEntity.ok(this.benhService.getAllBenhByKhoaId(u.getKhoaId().getId(), page, pageSize));
    }
    @PostMapping("/benh")
    public ResponseEntity<?> createBenh(Principal principal, @RequestBody Benh benh){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        benh.setCreatedDate(LocalDate.now());
        benh.setKhoa(u.getKhoaId());
        this.benhService.save(benh);
        return ResponseEntity.ok(benh);
        
    }
}
