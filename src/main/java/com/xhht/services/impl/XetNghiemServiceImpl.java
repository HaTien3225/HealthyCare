/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.XetNghiemRepository;
import com.xhht.services.XetNghiemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hatie
 */
@Service
public class XetNghiemServiceImpl implements XetNghiemService{
    
    @Autowired
    private XetNghiemRepository xetNghiemRepo;
    @Override
    public List<XetNghiem> getALlXetNghiem(int donKhamId) {
        return this.xetNghiemRepo.getALlXetNghiem(donKhamId);
    }
}
