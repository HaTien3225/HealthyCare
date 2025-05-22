/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.DanhGia;
import com.xhht.repositories.DanhGiaRepository;
import com.xhht.services.DanhGiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class DanhGiaServiceImpl implements DanhGiaService{

    @Autowired
    private DanhGiaRepository danhGiaRepo;
    
    @Override
    public List<DanhGia> getAllDanhGia(Integer benhNhanId, Integer bacSiId, Boolean isBinhLuan, Boolean isPhanHoi) {
        return danhGiaRepo.getAllDanhGia(benhNhanId, bacSiId, isBinhLuan, isPhanHoi);
    }

    @Override
    public DanhGia getDanhGiaById(Integer danhGiaId) {
        return danhGiaRepo.getDanhGiaById(danhGiaId);
    }

    @Override
    public DanhGia createOrUpdate(DanhGia danhGia) {
        return danhGiaRepo.createOrUpdate(danhGia);
    }
    
}
