/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.repositories.DonKhamRepository;
import com.xhht.services.DonKhamService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class DonKhamServiceImpl implements DonKhamService{
    @Autowired
    DonKhamRepository donKhamRepo;

    @Override
    public BigDecimal getTotalRevenue(int month, int year) {
        
        return this.donKhamRepo.getTotalRevenue(month, year);       
    }
}
