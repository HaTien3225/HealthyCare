/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.xhht.pojo.GiayPhepHanhNghe;
import com.xhht.pojo.User;
import com.xhht.services.EmailService;
import com.xhht.services.GiayPhepHanhNgheService;
import com.xhht.services.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hatie
 */
@RestController
@RequestMapping("/api/doctor")
public class ApiDoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private GiayPhepHanhNgheService giayPhepService;
    
     @Autowired
    private EmailService emailService;

    @PostMapping("/upload-license")
    public ResponseEntity<?> uploadLicense(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng chọn file!");
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String giayPhepUrl = (String) uploadResult.get("secure_url");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User doctor = userService.getUserByUsername(username);

            GiayPhepHanhNghe gphn = new GiayPhepHanhNghe();
            gphn.setImage(giayPhepUrl);
            gphn.setBacSiId(doctor);
            gphn.setIsValid(false);
            gphn.setCreated_date(LocalDate.now());

            giayPhepService.createOrUpdate(gphn);
            
            doctor.setGiayPhepHanhNgheId(gphn);
            emailService.sendSimpleEmail(
                    doctor.getEmail(),
                    "Cập nhật giấy phép hành nghề",
                    "Giấy phép hành nghề của bạn đã được gửi đi, vui lòng chờ phản hồi từ admin"
            );
            userService.createOrUpdate(doctor);
            
           
            return ResponseEntity.ok("Tải giấy phép hành nghề thành công!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi upload!");
        }
    }
}
