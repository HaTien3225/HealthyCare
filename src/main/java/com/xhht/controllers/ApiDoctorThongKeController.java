/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.repositories.LichKhamRepository;
import com.xhht.repositories.UserRepository;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private LichKhamRepository lichkhamRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/thongke")
    public Map<String, Long> getThongKe(Principal principal) {
        String username = principal.getName();

        User bacSi = userRepository.getUserByUsername(username);
        if (bacSi == null) {
            throw new RuntimeException("Không tìm thấy thông tin bác sĩ!");
        }

        int bacSiId = bacSi.getId();

        long totalAppointments = lichkhamRepository.countByBacSiIdAndDaKhamTrue(bacSiId);
        long pendingAppointments = lichkhamRepository.countByBacSiIdAndDaKhamFalse(bacSiId);

        Map<String, Long> result = new HashMap<>();
        result.put("totalAppointments", totalAppointments);
        result.put("pendingAppointments", pendingAppointments);
        return result;
    }

    @GetMapping("/benhphobien")
    public Map<String, Long> getBenhPhoBienTheoThang(@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "quarter", required = false) Integer quarter,
            Principal principal) {
        String username = principal.getName();
        User bacSi = userRepository.getUserByUsername(username);
        if (bacSi == null) {
            throw new RuntimeException("Không tìm thấy thông tin bác sĩ!");
        }

        int bacSiId = bacSi.getId();
        Map<String, Long> result;

        if (month != null) {
            result = lichkhamRepository.getBenhPhoBienTheoThang(bacSiId, month);
        } else if (quarter != null) {
            result = lichkhamRepository.getBenhPhoBienTheoQuy(bacSiId, quarter);
        } else {
            throw new IllegalArgumentException("Phải cung cấp tháng hoặc quý để thống kê!");
        }

        return result;
    }

}
