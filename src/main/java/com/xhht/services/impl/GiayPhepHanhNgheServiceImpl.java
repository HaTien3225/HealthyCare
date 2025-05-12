/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.repositories.GiayPhepHanhNgheRepository;
import com.xhht.services.GiayPhepHanhNgheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class GiayPhepHanhNgheServiceImpl implements GiayPhepHanhNgheService{

    @Autowired
    private GiayPhepHanhNgheRepository giayPhepRepo;
    
    @Override
    public void updateGiayPhepStatus(int id, Boolean isActive) {
        giayPhepRepo.updateGiayPhepStatus(id, isActive);
    }
    
}
