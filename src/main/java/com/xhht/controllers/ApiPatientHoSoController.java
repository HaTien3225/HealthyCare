/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.repositories.HoSoSucKhoeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
public class ApiPatientHoSoController {

    @Autowired
    private HoSoSucKhoeRepository hoSoSucKhoeRepository;

    // Lấy hồ sơ bệnh nhân theo ID
    @GetMapping("/user/api/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> getHoSoSucKhoe(@PathVariable int benhNhanId) {
        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            return ResponseEntity.ok(optionalHoSo.get());
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân với ID = " + benhNhanId);
        }
    }

    // Cập nhật hồ sơ bệnh nhân
    @PutMapping("/user/api/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> updateHoSoSucKhoe(
            @PathVariable int benhNhanId,
            @RequestBody HoSoSucKhoe hoSoDetails) {

        Optional<HoSoSucKhoe> optionalHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        if (optionalHoSo.isPresent()) {
            HoSoSucKhoe hoSo = optionalHoSo.get();
            hoSo.setChieuCao(hoSoDetails.getChieuCao());
            hoSo.setCanNang(hoSoDetails.getCanNang());
            hoSo.setBirth(hoSoDetails.getBirth());

            HoSoSucKhoe updated = hoSoSucKhoeRepository.save(hoSo);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân với ID = " + benhNhanId);
        }
    }

    @PostMapping("/user/api/hosobenhnhan")
    public ResponseEntity<?> createHoSoSucKhoe(@RequestBody HoSoSucKhoe hoSoSucKhoe) {
        Optional<HoSoSucKhoe> existingHoSo = hoSoSucKhoeRepository.findByBenhNhanId(hoSoSucKhoe.getBenhNhanId().getId());
        if (existingHoSo.isPresent()) {
            return ResponseEntity.status(400).body("Hồ sơ bệnh nhân đã tồn tại.");
        }

        HoSoSucKhoe savedHoSo = hoSoSucKhoeRepository.save(hoSoSucKhoe);
        return ResponseEntity.status(201).body(savedHoSo);
    }

    @DeleteMapping("/user/api/hosobenhnhan/{benhNhanId}")
    public ResponseEntity<?> deleteHoSoSucKhoe(@PathVariable int benhNhanId) {
        Optional<HoSoSucKhoe> existingHoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        if (existingHoSo.isPresent()) {
            hoSoSucKhoeRepository.delete(existingHoSo.get());
            return ResponseEntity.status(200).body("Hồ sơ bệnh nhân đã được xóa.");
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy hồ sơ bệnh nhân với ID = " + benhNhanId);
        }
    }

}
