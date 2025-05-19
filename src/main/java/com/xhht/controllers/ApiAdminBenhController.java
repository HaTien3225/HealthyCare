/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.Benh;
import com.xhht.services.BenhService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@CrossOrigin
public class ApiAdminBenhController {

    @Autowired
    private BenhService benhService;

    @GetMapping("/admin/api/benhs")
    public ResponseEntity<List<Benh>> listBenhByKhoaId(
            @RequestParam(name = "khoaid", required = true) int khoaId,
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return new ResponseEntity<>(this.benhService.getAllBenhByKhoaId(khoaId, page, 10), HttpStatus.OK);
    }
}
