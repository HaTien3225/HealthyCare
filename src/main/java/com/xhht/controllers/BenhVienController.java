/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.BenhVien;
import com.xhht.services.BenhVienService;
import java.util.List;
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
public class BenhVienController {

    @Autowired
    private BenhVienService benhVienService;

    @GetMapping("/admin/benhviens")
    public String benhVienView(@RequestParam(name = "kw", required = false) String keyword, Model model) {
        List<BenhVien> benhViens = benhVienService.getBenhViens(keyword);
        model.addAttribute("benhViens", benhViens);
        model.addAttribute("keyword", keyword);
        return "benhvien_admin";
    }

    @GetMapping("/admin/benhvien/create-form")
    public String createBenhVienForm(Model model) {
        model.addAttribute("benhvien", new BenhVien());
        return "benhvien_admin_create";
    }

    @PostMapping("/admin/benhvien/create")
    public String benhVienCreate(@ModelAttribute(value = "benhvien") BenhVien bv, RedirectAttributes redirectAttributes  ) {
        try {
            this.benhVienService.createOrUpdate(bv);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo bệnh viện thành công!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo bệnh viện thất bại: " + ex.getMessage());
        }
        return "redirect:/admin/benhviens";
    }
}
