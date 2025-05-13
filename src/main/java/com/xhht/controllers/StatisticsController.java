/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.controllers;

import com.xhht.services.DonKhamService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lehuy
 */
@Controller
public class StatisticsController {
    
    @Autowired
    private DonKhamService donKhamService;
    
    @GetMapping("/admin/statistics")
    public String statisticsView(Model model, @RequestParam (name = "year",required = false) String yearStr){
        if(yearStr == null || yearStr.isEmpty())
            yearStr =  String.valueOf(LocalDate.now().getYear());
        int year = Integer.parseInt(yearStr);
        if(year <=0)
            year = LocalDate.now().getYear();
        List<BigDecimal> monthlyRevenue = new ArrayList<BigDecimal>();
        for(int i = 1;i<13;i++){
            monthlyRevenue.add(this.donKhamService.getTotalRevenue(i, year));
        }
        BigDecimal totalRevenue = monthlyRevenue.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("monthlyRevenue",monthlyRevenue);
        model.addAttribute("totalRevenue",totalRevenue);
        model.addAttribute("syear",year);
        return "statistics_admin";       
    }
    
}
