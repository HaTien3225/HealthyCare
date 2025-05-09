/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.User;
import com.xhht.services.RoleService;
import com.xhht.services.UserService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author lehuy
 */
@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping("/login")
    public String loginView(){
        return "login";
    }
    
    @GetMapping("/admin/users")
    public String userManagementView(){
        return "user_manage";
    }
    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute(value = "user")User user,RedirectAttributes redirectAttributes,@RequestParam("roleId") int roleId){
        user.setCreatedDate(LocalDate.now());
        user.setRole(this.roleService.getRoleById(roleId));
        try {
            this.userService.createOrUpdate(user);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo user thất bại: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    @GetMapping("/admin/users/create-form")
    public String createUserForm(Model model){
        User u = new User();
        u.setIsActive(false);
        model.addAttribute("user",u);
        model.addAttribute("roles",this.roleService.getAllRole());
        return "user_create_form";
    }
}
