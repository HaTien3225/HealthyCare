package com.xhht.controllers;

import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ApiDoctorLichKhamController {

    @Autowired
    private LichKhamRepository lichkhamRepository;

    // Lấy danh sách lịch khám của bác sĩ (chưa khám)
    @GetMapping("/doctor/api/lichkham")
    public ResponseEntity<?> getLichkhamForDoctor(@RequestParam Long bacSiId) {
        List<LichKham> lichkhams = lichkhamRepository.findByBacSiIdAndDaKhamFalse(bacSiId);
        return ResponseEntity.ok(lichkhams);
    }

    
    @PutMapping("/doctor/api/lichkham/{id}")
    public ResponseEntity<?> updateLichkham(@PathVariable int id, @RequestBody LichKham lichkhamDetails) {
        Optional<LichKham> optional = lichkhamRepository.findById(id);

        if (optional.isPresent()) {
            LichKham lichkham = optional.get();
            lichkham.setDaKham(lichkhamDetails.getDaKham());
            // Có thể cập nhật thêm các trường khác tại đây nếu cần
            return ResponseEntity.ok(lichkhamRepository.save(lichkham));
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy lịch khám với ID = " + id);
        }
    }
}
