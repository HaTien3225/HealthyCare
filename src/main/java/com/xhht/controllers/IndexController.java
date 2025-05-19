/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lehuy
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

//    @ModelAttribute
//    public void commonResponses(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Lấy username hiện tại
//        String username = authentication.getName();
//
//        // Lấy User từ DB
//        User user = userService.getUserByUsername(username);
//
//        if (user != null) {
//            model.addAttribute("currentUserFullName", user.getHo() + " " + user.getTen());
//        }
//    }
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      
        String username = authentication.getName();

        
        User user = userService.getUserByUsername(username);
        String role = user.getRole().getRole();
        
        

        if (user != null) {
            model.addAttribute("currentUser", user);
            model.addAttribute("userrole", role);
            if(role.equals("ROLE_DOCTOR"))
            {   
                model.addAttribute("giayphep",user.getGiayPhepHanhNgheId());
            }else{
                model.addAttribute("giayphep",null);
            }
        }
        return "profile";
    }

    @GetMapping("/admin/workspace")
    public String workspace_admin() {
        return "workspace_admin";
    }

    @GetMapping("/doctor/workspace")
    public String workspace_doctor(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      
        String username = authentication.getName();

      
        User user = userService.getUserByUsername(username);

        if (user != null && user.getRole().getId() == 3) {
            
            if (!user.isIsActive()) {
                return "redirect:/doctor/upload-profile"; 
            }

            model.addAttribute("currentDoctor", user);
        }

        return "workspace_doctor";  
    }

    
    @GetMapping("/patient/workspace")
    public String workspace_patient(Model model) {
        return "workspace_patient";
    }
}
