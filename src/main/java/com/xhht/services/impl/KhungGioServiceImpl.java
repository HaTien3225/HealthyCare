/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.KhungGio;
import com.xhht.services.KhungGioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhht.repositories.KhungGioRepository;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lehuy
 */
@Service
public class KhungGioServiceImpl implements KhungGioService{
    
    @Autowired
    KhungGioRepository khungGioRepo;
    
    @Override
    public KhungGio findKhungGioById(int id) {
        return this.khungGioRepo.findKhungGioById(id);
    }

    @Override
    public List<KhungGio> findKhungGioDaDatCuaBacSiTrongNgay(int bacSiId, LocalDate ngay) {
        return this.khungGioRepo.findKhungGioDaDatCuaBacSiTrongNgay(bacSiId, ngay);
    }

    @Override
    public List<KhungGio> getAllKhungGio() {
        return this.khungGioRepo.getAllKhungGio();
    }
    
}
