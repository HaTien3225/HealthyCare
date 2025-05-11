/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.services.BenhVienService;
import com.xhht.services.KhoaService;
import com.xhht.services.RoleService;
import com.xhht.services.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private KhoaService khoaService;
    
    @Autowired
    private BenhVienService benhVienService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute(value = "user") User user, RedirectAttributes redirectAttributes, 
            @RequestParam(name = "roleId",required = true) int roleId
            ,@RequestParam(name = "khoaid" ,required = false) String khoaIdStr 
    ) {
        user.setCreatedDate(LocalDate.now());       
        user.setRole(this.roleService.getRoleById(roleId));        
        if(khoaIdStr != null && !khoaIdStr.isEmpty()){
            Integer khoaId = Integer.parseInt(khoaIdStr);
            user.setKhoaId(this.khoaService.getKhoaByKhoaId(khoaId));
        }
        try {
            this.userService.createOrUpdate(user);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo user thất bại: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/create-form")
    public String createUserForm(Model model) {
        User u = new User();
        u.setIsActive(false);
        model.addAttribute("user", u);
        model.addAttribute("benhviens", this.benhVienService.getBenhViens(null));
        model.addAttribute("roles", this.roleService.getAllRole());
        return "user_create_form";
    }

    @GetMapping("/admin/users")
    public String listUser(Model model, @RequestParam Map<String, String> params) {
        String kw = params.get("kw");
        String page = params.getOrDefault("page", "1");
        List<User> users = this.userService.getAllUser(params);
        List<Role> roles = this.roleService.getAllRole();       
        model.addAttribute("kwr", kw);
        model.addAttribute("page", page);
        model.addAttribute("user", users);
        model.addAttribute("roles", roles);
        model.addAttribute("benhviens", this.benhVienService.getBenhViens(null));
        return "user_manage";
    }

    @GetMapping("/admin/users/{id}")
    public String userDetailView(Model model, @PathVariable(value = "id", required = true) int id) {
        model.addAttribute("user", this.userService.getUserById(id));
        return "user_detail_admin";
    }
}
