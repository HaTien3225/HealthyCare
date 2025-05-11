package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author hatie
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    // Trang quản lý bác sĩ chưa được xác nhận
    @GetMapping("/admin/verify-doctors")
    public String viewDoctorsPendingVerification(Model model) {
        List<User> pendingDoctors = userService.getDoctorsPendingVerification();
        model.addAttribute("pendingDoctors", pendingDoctors);
        return "/verify_doctors";  // Trang để hiển thị bác sĩ chưa được xác nhận
    }

    @PostMapping("/admin/verify-doctor/{id}")
    public String verifyDoctor(@PathVariable("id") int id) {
        userService.verifyDoctor(id);
        return "redirect:/admin/verify-doctors";
    }
}
