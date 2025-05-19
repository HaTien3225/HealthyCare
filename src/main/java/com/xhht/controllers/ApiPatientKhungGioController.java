/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.KhungGio;
import com.xhht.services.KhungGioService;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class ApiPatientKhungGioController {

    @Autowired
    private KhungGioService khungGioService;

    @GetMapping("/khunggiotrong/{bacSiId}")
    public ResponseEntity<?> getKhungGioTrong(@PathVariable(name = "bacSiId", required = true) Integer bacSiId, @RequestParam(name = "ngay", required = true) LocalDate ngay) {
        if (bacSiId != null && ngay != null) {

            List<KhungGio> daDatList = this.khungGioService.findKhungGioDaDatCuaBacSiTrongNgay(bacSiId, ngay);

            List<KhungGio> allKhungGioList = this.khungGioService.getAllKhungGio();

            Map<String, Boolean> result = new LinkedHashMap<>();

            for (KhungGio kg : allKhungGioList) {
                boolean isBooked = daDatList.stream().anyMatch(k -> k.getId().equals(kg.getId()));
                result.put(kg.getTenKg(), isBooked);
            }

            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
    }

    @GetMapping("/khunggio")
    public ResponseEntity<?> listKhungGio() {
        return ResponseEntity.ok(this.khungGioService.getAllKhungGio());
    }

}
