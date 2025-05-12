/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.TinNhan;
import com.xhht.repositories.TinNhanRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hatie
 */
@RestController
@CrossOrigin
public class ApiDoctorTuvanController {
@Autowired
private TinNhanRepository tinnhanRepository;
    @PostMapping("/doctor/api/doctor/tuvan")
    public TinNhan sendTinnhan(@RequestBody TinNhan tinnhan) {
        return tinnhanRepository.save(tinnhan);
    }

    @GetMapping("/doctor/api/tuvan/{senderId}/{receiverId}")
    public List<TinNhan> getTinnhan(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return tinnhanRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

}
