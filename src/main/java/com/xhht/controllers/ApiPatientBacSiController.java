/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.services.BenhVienService;
import com.xhht.services.KhoaService;
import com.xhht.services.UserService;
import jakarta.faces.annotation.RequestMap;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ApiPatientBacSiController {
    
    @Autowired
    private BenhVienService benhVienService;
    
    @Autowired
    private KhoaService khoaService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/benhvien")
    public ResponseEntity<?> listBenhVien(@RequestParam(name = "kw",required = false) String kw) {
        if(kw == null)
            kw = "";
        return ResponseEntity.ok(this.benhVienService.getBenhViens(kw));
    }
    
    @GetMapping("/benhvien/khoa")
    public ResponseEntity<?> listKhoa(@RequestParam(name = "benhVienId",required = false) Integer benhVienId,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "pageSize",required = false) Integer pageSize) {
        if (benhVienId != null) {
            if(page == null)
                page = 1;
            if(pageSize == null)
                pageSize = 10;
            return ResponseEntity.ok(this.khoaService.getAllKhoaByBenhVienId(benhVienId, page, pageSize));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
    }
    
    @GetMapping("/bacsi")
    public ResponseEntity<?> listBacSi(@RequestParam(name = "khoaId",required = false) Integer khoaId,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "kw",required = false) String kw,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (khoaId != null) {
            if(kw == null)
                kw ="";
            if(page == null)
                page = 1;
            if(pageSize == null)
                pageSize = 100;
 
            Map<String, String> params = new HashMap<>();
            params.put("isActive", "true");
            params.put("roleId", "3");
            params.put("khoaid", String.valueOf(khoaId));
            params.put("kw", kw);
            params.put("pageSize", String.valueOf( pageSize));
            
            return ResponseEntity.ok(this.userService.getAllUser(params));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        
    }
}
