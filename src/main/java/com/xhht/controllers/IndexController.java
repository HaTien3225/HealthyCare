/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.services.BenhVIenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lehuy
 */
@Controller
public class IndexController {
    
    @Autowired
    private BenhVIenService benhVIenService;
    
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("benhviens", this.benhVIenService.getBenhViens());
        return "index";
    }
}
