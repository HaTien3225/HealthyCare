/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.Khoa;
import com.xhht.services.KhoaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@CrossOrigin
public class ApiAdminKhoaController {
    @Autowired
    private KhoaService khoaService;
    
    @GetMapping("/admin/api/khoas")
    public ResponseEntity<List<Khoa>> listKhoaByBenhVienId(
        @RequestParam(name = "benhvienid", required = true) int benhVienId,
        @RequestParam(name = "page", defaultValue = "1") int page){
        return new ResponseEntity<>( this.khoaService.getAllKhoaByBenhVienId(benhVienId, page, 10),HttpStatus.OK);
    }
    
    
}
