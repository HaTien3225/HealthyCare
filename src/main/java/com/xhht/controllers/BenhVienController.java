/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.BenhVien;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.xhht.services.BenhVienService;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    @GetMapping("/admin/benhvien/{benhVienId}")
    public String benhVienDetail(Model model, @PathVariable(value = "benhVienId") int id){
        BenhVien benhVien = this.benhVienService.getBenhVienById(id);
        model.addAttribute("benhvien",benhVien);
        return "benhvien_detail";
    }
    @PostMapping("/admin/benhvien/update")
    public String benhVienUpdate(@ModelAttribute(value = "benhvien") BenhVien bv){
        this.benhVienService.createOrUpdate(bv);      
        return "redirect:/admin/benhviens";
    }
}
