/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.User;
import com.xhht.services.HoSoSucKhoeService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiPatientHoSoController {

    @Autowired
    private HoSoSucKhoeService hoSoSucKhoeService;

    @Autowired
    private UserService userService;

    
    @GetMapping("/hososuckhoe")
    public ResponseEntity<?> getHoSoSucKhoe(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeService.getHoSoByBenhNhanId(u.getId());
        if(optionalHoSo.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        return ResponseEntity.ok(optionalHoSo.get());
    }

   
    @PutMapping("/hososuckhoe")
    public ResponseEntity<?> updateHoSoSucKhoe(
            Principal principal,
            @RequestBody HoSoSucKhoe hoSoDetails) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeService.getHoSoByBenhNhanId(u.getId());
        if (optionalHoSo.isPresent()) {
            HoSoSucKhoe hoSo = optionalHoSo.get();

            if (hoSoDetails.getChieuCao() != null) {
                hoSo.setChieuCao(hoSoDetails.getChieuCao());
            }

            if (hoSoDetails.getCanNang() != null) {
                hoSo.setCanNang(hoSoDetails.getCanNang());
            }

            if (hoSoDetails.getBirth() != null) {
                hoSo.setBirth(hoSoDetails.getBirth());
            }

            HoSoSucKhoe updated = hoSoSucKhoeService.saveOrUpdate(hoSo);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân");
        }
    }

    @PostMapping("/hososuckhoe")
    public ResponseEntity<?> createHoSoSucKhoe(@RequestBody HoSoSucKhoe hoSoSucKhoe, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<HoSoSucKhoe> existingHoSo = hoSoSucKhoeService.getHoSoByBenhNhanId(u.getId());
        if (existingHoSo.isPresent()) {
            return ResponseEntity.status(400).body("Hồ sơ bệnh nhân đã tồn tại.");
        }
        hoSoSucKhoe.setBenhNhanId(this.userService.getUserById(u.getId()));
        HoSoSucKhoe savedHoSo = hoSoSucKhoeService.saveOrUpdate(hoSoSucKhoe);
        return ResponseEntity.status(201).body(savedHoSo);
    }

//    @DeleteMapping("/hosobenhnhan/{benhNhanId}")
//    public ResponseEntity<?> deleteHoSoSucKhoe(@PathVariable int benhNhanId) {
//        Optional<HoSoSucKhoe> existingHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
//        if (existingHoSo.isPresent()) {
//            hoSoSucKhoeRepository.delete(existingHoSo.get());
//            return ResponseEntity.status(200).body("Hồ sơ bệnh nhân đã được xóa.");
//        } else {
//            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân với ID = " + benhNhanId);
//        }
//    }
}
