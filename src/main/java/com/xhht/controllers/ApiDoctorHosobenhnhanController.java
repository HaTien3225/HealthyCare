package com.xhht.controllers;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.repositories.HoSoSucKhoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorHosobenhnhanController {

    @Autowired
    private HoSoSucKhoeRepository hoSoSucKhoeRepository;

    // Lấy hồ sơ bệnh nhân
    @GetMapping("/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> getHoSoSucKhoe(@PathVariable("benhNhanId") int benhNhanId) {
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            return ResponseEntity.ok(optionalHoSo.get());
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân có ID = " + benhNhanId);
        }
    }

    // Cập nhật hồ sơ bệnh nhân
    @PutMapping("/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> updateHoSoSucKhoe(
            @PathVariable("benhNhanId") int benhNhanId,
            @RequestBody HoSoSucKhoe hoSoDetails) {

        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            HoSoSucKhoe hoSo = optionalHoSo.get();
            hoSo.setChieuCao(hoSoDetails.getChieuCao());
            hoSo.setCanNang(hoSoDetails.getCanNang());
            hoSo.setBirth(hoSoDetails.getBirth());

            HoSoSucKhoe updated = hoSoSucKhoeRepository.createOrUpdate(hoSo);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân có ID = " + benhNhanId);
        }
    }
}
