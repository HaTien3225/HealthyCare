/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.xhht.pojo.GiayPhepHanhNghe;
import com.xhht.pojo.User;
import com.xhht.services.GiayPhepHanhNgheService;
import com.xhht.services.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hatie
 */
@Controller
public class DoctorController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private GiayPhepHanhNgheService giayPhepService;

    @GetMapping("/doctor/upload-profile")
    public String showUploadForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User doctor = userService.getUserByUsername(username);

        model.addAttribute("doctor", doctor);
        return "doctor_upload_profile";
    }

    @PostMapping("/doctor/upload-profile")
    public String handleUpload(
            @RequestParam("giayphephanhnghe") MultipartFile giayphephanhnghe,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (giayphephanhnghe == null || giayphephanhnghe.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng chọn tệp trước khi tải lên!");
                return "redirect:/doctor/upload-profile";
            }

            
            System.out.println("Tên file: " + giayphephanhnghe.getOriginalFilename());
            System.out.println("Kích thước file: " + giayphephanhnghe.getSize());

           
            Map uploadResult = cloudinary.uploader().upload(giayphephanhnghe.getBytes(), ObjectUtils.emptyMap());
            String giayphepUrl = (String) uploadResult.get("secure_url");

          
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User doctor = userService.getUserByUsername(username);

            
            GiayPhepHanhNghe giayPhep = new GiayPhepHanhNghe();
            giayPhep.setImage(giayphepUrl);
            giayPhep.setBacSiId(doctor);
            giayPhep.setIsValid(true);
            giayPhep.setCreated_date(LocalDate.now());
            giayPhepService.createOrUpdate(giayPhep);

            doctor.setGiayPhepHanhNgheId(giayPhep);
            userService.createOrUpdate(doctor);

            redirectAttributes.addFlashAttribute("message", "Tải hồ sơ thành công!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi upload hồ sơ!");
        }

        return "redirect:/doctor/workspace";
    }

}
