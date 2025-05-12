/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.repositories.LichKhamRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
public class ApiDoctorThongKeController {
@Autowired
private LichKhamRepository lichkhamRepository;

    @GetMapping("/doctor/api/thongke/{bacSiId}")
    public Map<String, Long> getThongKe(@PathVariable Long bacSiId) {
        long totalAppointments = lichkhamRepository.countByBacSiIdAndDaKhamTrue(bacSiId);
        long pendingAppointments = lichkhamRepository.countByBacSiIdAndDaKhamFalse(bacSiId);

        Map<String, Long> result = new HashMap<>();
        result.put("totalAppointments", totalAppointments);
        result.put("pendingAppointments", pendingAppointments);
        return result;
    }

}
