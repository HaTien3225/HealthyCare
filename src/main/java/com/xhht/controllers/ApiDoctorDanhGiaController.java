package com.xhht.controllers;

import com.xhht.pojo.DanhGia;
import com.xhht.pojo.User;
import com.xhht.services.DanhGiaService;
import com.xhht.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorDanhGiaController {

    @Autowired
    private UserService userService;

    @Autowired
    private DanhGiaService danhGiaService;

    // Lấy tất cả đánh giá dành cho bác sĩ hiện tại
    @GetMapping("/danhgia")
    public ResponseEntity<?> getDanhGia(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        return ResponseEntity.ok(this.danhGiaService.getAllDanhGia(null, u.getId(), true, false));
    }

    // Trả lời đánh giaá
    @PutMapping("/danhgia/{danhGiaId}/reply")
    public ResponseEntity<?> replyDanhGia(@PathVariable("danhGiaId") Integer danhGiaId,
            @RequestBody String phanHoi,
            Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        DanhGia dg = this.danhGiaService.getDanhGiaById(danhGiaId);
        if (dg == null || dg.getBacSiId() == null || !dg.getBacSiId().getId().equals(u.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đánh giá hợp lệ");
        }

        dg.setPhanHoi(phanHoi);

        return ResponseEntity.ok(this.danhGiaService.createOrUpdate(dg));
    }

    @GetMapping("/danhgia/{id}")
    public ResponseEntity<?> getDanhGiaById(@PathVariable("id") Integer id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        DanhGia dg = this.danhGiaService.getDanhGiaById(id);
        if (dg == null || dg.getBacSiId() == null || !dg.getBacSiId().getId().equals(u.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đánh giá hợp lệ");
        }

        return ResponseEntity.ok(dg);
    }

}
