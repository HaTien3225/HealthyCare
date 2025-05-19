/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.services.LichKhamService;
import com.xhht.services.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@RequestMapping("/api/doctor")
@CrossOrigin
public class ApiDoctorThongKeController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private UserService userService;

    @GetMapping("/thongke")
    public Map<String, Long> getThongKe(Principal principal) {
        if (principal == null) {
            return (Map<String, Long>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return (Map<String, Long>) ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        String username = principal.getName();

        User bacSi = userService.getUserByUsername(username);
        if (bacSi == null) {
            throw new RuntimeException("Không tìm thấy thông tin bác sĩ!");
        }

        int bacSiId = bacSi.getId();

        long totalAppointments = lichKhamService.countLichDaKham(bacSiId);
        long pendingAppointments = lichKhamService.countLichChuaKham(bacSiId);

        Map<String, Long> result = new HashMap<>();
        result.put("totalAppointments", totalAppointments);
        result.put("pendingAppointments", pendingAppointments);
        return result;
    }

    @GetMapping("/benhphobien")
    public Map<String, Long> getBenhPhoBienTheoThang(@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "quarter", required = false) Integer quarter,
            Principal principal) {
        if (principal == null) {
            return (Map<String, Long>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
        User u = this.userService.getUserByUsername(principal.getName());
        if (!u.getRole().getRole().equals("ROLE_DOCTOR")) {
            return (Map<String, Long>) ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }
        String username = principal.getName();
        User bacSi = userService.getUserByUsername(username);
        if (bacSi == null) {
            throw new RuntimeException("Không tìm thấy thông tin bác sĩ!");
        }

        int bacSiId = bacSi.getId();
        Map<String, Long> result;

        if (month != null) {
            result = lichKhamService.getBenhPhoBienTheoThang(bacSiId, month);
        } else if (quarter != null) {
            result = lichKhamService.getBenhPhoBienTheoQuy(bacSiId, quarter);
        } else {
            throw new IllegalArgumentException("Phải cung cấp tháng hoặc quý để thống kê!");
        }

        return result;
    }

}
