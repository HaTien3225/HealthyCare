package com.xhht.controllers;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.User;
import com.xhht.services.DonKhamService;
import com.xhht.services.HoSoSucKhoeService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.time.LocalDate;
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

    @Autowired
    private DonKhamService donKhamService;

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

    @GetMapping("/viewdonkham")
    public ResponseEntity<?> listDonKham(Principal principal, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "benhNhanId", required = true) Integer benhNhanId,
            @RequestParam(name = "kw", required = false) String kw,
            @RequestParam(name = "ngay", required = false) LocalDate ngay) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (kw == null) {
            kw = null;
        }
        return ResponseEntity.ok(this.donKhamService.getAllDonKham(benhNhanId, true, page, pageSize, kw, ngay, null));
    }

    @GetMapping("/viewdonkham/{donKhamId}")
    public ResponseEntity<?> detailDonKham(Principal principal, @PathVariable(name = "donKhamId", required = true) Integer donKhamId) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        return ResponseEntity.ok(this.donKhamService.getAllChiTietDonKham(donKhamId));
    }
}
