/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.DanhGia;
import com.xhht.pojo.User;
import com.xhht.services.DanhGiaService;
import com.xhht.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiPatientDanhGiaController {
    @Autowired
    private UserService userService;
    @Autowired 
    private DanhGiaService danhGiaService;
    
    @GetMapping("/danhgia")
    public ResponseEntity<?> getDanhGia(Principal principal,
            @RequestParam(name = "isBinhLuan",required = false) Boolean isBinhLuan,@RequestParam(name = "isPhanHoi",required = false) Boolean isPhanHoi ){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(!u.isIsActive())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This account has been disabled!");
        return ResponseEntity.ok(this.danhGiaService.getAllDanhGia(u.getId(), null, isBinhLuan, isPhanHoi));
        
        
    }
    
    @GetMapping("/danhgia/{danhGiaId}")
    public ResponseEntity<?> detailDanhGia(@PathVariable(name = "danhGiaId",required = true)Integer danhGiaId,Principal principal){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(!u.isIsActive())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This account has been disabled!");
        return ResponseEntity.ok(this.danhGiaService.getDanhGiaById(danhGiaId));
    }
    @PutMapping("/danhgia/{danhGiaId}")
    public ResponseEntity<?> updateDanhGia(@RequestBody DanhGia danhGia,Principal principal,@PathVariable(name = "danhGiaId",required = true) Integer danhGiaId){
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if(!u.isIsActive())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This account has been disabled!");
        DanhGia dg = this.danhGiaService.getDanhGiaById(danhGiaId);
        danhGia.setId(danhGiaId);
        danhGia.setBacSiId(dg.getBacSiId());
        danhGia.setBenhNhanId(dg.getBenhNhanId());
        danhGia.setLichKhamId(dg.getLichKhamId());
        danhGia.setPhanHoi(null);
        return ResponseEntity.ok(this.danhGiaService.createOrUpdate(danhGia));
    }
}   
    
