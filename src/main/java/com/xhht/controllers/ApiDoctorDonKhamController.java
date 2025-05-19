/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.DonKham;
import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.User;
import com.xhht.services.BenhService;
import com.xhht.services.DonKhamService;
import com.xhht.services.LichKhamService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lehuy
 */
@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorDonKhamController {

    @Autowired
    private UserService userService;

    @Autowired
    private DonKhamService donKhamService;
    
    @Autowired
    private LichKhamService lichKhamService;
    
    @Autowired
    private BenhService benhService;

    @PostMapping("/donkham")
    public ResponseEntity<?> createDonKham(Principal principal, @RequestBody DonKham donKham,
            @RequestParam(name = "benhNhanId") Integer benhNhanId, @RequestParam(name = "benhId") Integer benhId,
            @RequestParam(name = "lichKhamId") Integer lichKhamId) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        HoSoSucKhoe hssk;
        try{
            hssk = userService.getUserById(benhNhanId).getHoSoSucKhoeId();
            if(hssk == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("benh nhan chua co ho so suc khoe");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Loi!");
        }
        donKham.setBacSiId(u);
        donKham.setHoSoSucKhoeId(hssk);
        donKham.setIsPaid(Boolean.FALSE);
        donKham.setCreatedDate(LocalDate.now());
        donKham.setBenhId(benhService.getBenhById(benhId));
        donKham.setLichKhamId(this.lichKhamService.getLichKhamById(lichKhamId).get());
        this.donKhamService.save(donKham);
        return ResponseEntity.ok(donKham);  
        }
    }
