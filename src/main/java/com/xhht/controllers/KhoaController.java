/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.Khoa;
import com.xhht.services.BenhVienService;
import com.xhht.services.KhoaService;
import java.time.LocalDate;
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

/**
 *
 * @author lehuy
 */
@Controller
public class KhoaController {

    @Autowired
    private KhoaService khoaService;

    @Autowired
    private BenhVienService bvService;

    @GetMapping("/admin/khoas/{khoaId}")
    public String getDetailKhoa(Model model, @PathVariable(value = "khoaId", required = true) int id) {
        Khoa khoa = this.khoaService.getKhoaByKhoaId(id);
        model.addAttribute("khoa", khoa);
        model.addAttribute("tenbenhvien", khoa.getBenhvien().getTenBenhVien());
        model.addAttribute("benhvienid", khoa.getBenhvien().getId());
        return "khoa_detail";
    }

    @GetMapping("/admin/khoas/create_form")
    public String khoaCreateView(Model model, @RequestParam(name = "benhVienId", required = true) int benhVienId, @RequestParam(name = "tenBenhVien", required = true) String tenBenhVien) {
        model.addAttribute("khoa", new Khoa());
        model.addAttribute("benhVienId", benhVienId);
        model.addAttribute("tenBenhVien", tenBenhVien);
        return "khoa_create_form";
    }

    @PostMapping("/admin/khoas/create")
    public String createKhoa(@ModelAttribute(value = "khoa") Khoa khoa, RedirectAttributes redirectAttributes, @RequestParam(name = "benhVienId", required = true) int benhVienId) {
        khoa.setCreatedDate(LocalDate.now());

        khoa.setBenhvien(bvService.getBenhVienById(benhVienId));
        try {
            this.khoaService.createOrUpdate(khoa);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo khoa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo khoan thất bại: " + e.getMessage());
        }
        return "redirect:/admin/benhviens";
    }

    @PostMapping("/admin/khoas/update")
    public String updateKhoa(@ModelAttribute(value = "khoa") Khoa khoa, RedirectAttributes redirectAttributes,@RequestParam(name = "benhVienId", required = true) int benhVienId) {
        khoa.setCreatedDate(LocalDate.now());

        khoa.setBenhvien(bvService.getBenhVienById(benhVienId));
        try {
            this.khoaService.createOrUpdate(khoa);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khoa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật khoan thất bại: " + e.getMessage());
        }
        return "redirect:/admin/benhviens"+"/"+this.khoaService.getKhoaByKhoaId(khoa.getId()).getBenhvien().getId();
    }

}
