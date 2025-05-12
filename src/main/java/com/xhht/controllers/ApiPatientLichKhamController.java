/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
public class ApiPatientLichKhamController {

    @Autowired
    private LichKhamRepository lichKhamRepository;

    // Lấy danh sách lịch khám của bệnh nhân
    @GetMapping("/user/api/lichkham")
    public ResponseEntity<?> getLichKhamForPatient(@RequestParam Long benhNhanId) {
        List<LichKham> lichkhams = lichKhamRepository.findByBenhNhanId(benhNhanId);
        if (lichkhams.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy lịch khám cho bệnh nhân với ID = " + benhNhanId);
        }
        return ResponseEntity.ok(lichkhams);
    }

    // Cập nhật trạng thái lịch khám (đã khám hoặc chưa)
    @PutMapping("/user/lichkham/{id}")
    public ResponseEntity<?> updateLichKham(@PathVariable int id, @RequestBody LichKham lichkhamDetails) {
        Optional<LichKham> optionalLichKham = lichKhamRepository.findById(id);

        if (optionalLichKham.isPresent()) {
            LichKham lichKham = optionalLichKham.get();
            lichKham.setDaKham(lichkhamDetails.getDaKham());
            // Cập nhật các trường khác nếu cần
            return ResponseEntity.ok(lichKhamRepository.save(lichKham));
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
        }
    }

    @PostMapping("/user/api/lichkham")
    public ResponseEntity<?> createLichKham(@RequestBody LichKham lichKham) {
        LichKham saved = lichKhamRepository.save(lichKham);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/user/api/lichkham/{id}")
    public ResponseEntity<?> cancelLichKham(@PathVariable int id) {
        Optional<LichKham> optional = lichKhamRepository.findById(id);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            if (!lichKham.getDaKham()) {
                lichKhamRepository.delete(lichKham);
                return ResponseEntity.ok("Đã hủy lịch khám.");
            } else {
                return ResponseEntity.status(400).body("Không thể hủy vì lịch khám đã diễn ra.");
            }
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy lịch khám.");
        }
    }

}
