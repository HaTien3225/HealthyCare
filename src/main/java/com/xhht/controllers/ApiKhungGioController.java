/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.repositories.KhungGioRepository;
import com.xhht.services.KhungGioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiKhungGioController {
    
    @Autowired
    private KhungGioService khungGioService;
    
    @GetMapping("/khunggio")
    public ResponseEntity<?> listKhungGio(){        
        return ResponseEntity.ok(this.khungGioService.getAllKhungGio());
    }
    
}
