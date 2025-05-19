package com.xhht.controllers;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.User;
import com.xhht.services.HoSoSucKhoeService;
import com.xhht.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorHosobenhnhanController {

    @Autowired
    private HoSoSucKhoeService hoSoSucKhoeService;

    @Autowired
    private UserService userService;

    @GetMapping("/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> getHoSoSucKhoe(@PathVariable("benhNhanId") int benhNhanId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeService.getHoSoByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            return ResponseEntity.ok(optionalHoSo.get());
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân có ID = " + benhNhanId);
        }
    }

    @PutMapping("/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> updateHoSoSucKhoe(
            @PathVariable("benhNhanId") int benhNhanId,
            @RequestBody HoSoSucKhoe hoSoDetails, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeService.getHoSoByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            HoSoSucKhoe hoSo = optionalHoSo.get();
            hoSo.setChieuCao(hoSoDetails.getChieuCao());
            hoSo.setCanNang(hoSoDetails.getCanNang());
            hoSo.setBirth(hoSoDetails.getBirth());

            HoSoSucKhoe updated = hoSoSucKhoeService.saveOrUpdate(hoSo);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân có ID = " + benhNhanId);
        }
    }
}
