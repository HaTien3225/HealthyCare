/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.pojo.Benh;
import com.xhht.services.BenhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author lehuy
 */
@Controller
public class BenhController {
    @Autowired
    private BenhService benhService;
    
    @GetMapping("/admin/benhs/{benhId}")
    public String benhDetail(Model model, @PathVariable(value = "benhId", required = true) int id){
        Benh b = this.benhService.getBenhById(id);
        model.addAttribute("benh", b);
        model.addAttribute("tenkhoa",b.getKhoa().getTenKhoa());
        model.addAttribute("khoaid",b.getKhoa().getId());
        return "benh_detail";
    }
}
