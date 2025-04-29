/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.Khoa;
import com.xhht.repositories.KhoaRepository;
import com.xhht.services.KhoaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class KhoaServiceImpl implements KhoaService{

    @Autowired
    private KhoaRepository khoaRepo;
    
    @Override
    public List<Khoa> getAllKhoaByBenhVienId(int benhVienId, int page, int pageSize) {
       return this.khoaRepo.getAllKhoaByBenhVienId(benhVienId,page,pageSize);
    }

    @Override
    public Khoa getKhoaByKhoaId(int KhoaId) {
        return this.khoaRepo.getKhoaByKhoaId(KhoaId);
    }

    @Override
    public Khoa createOrUpdate(Khoa khoa) {
        return this.khoaRepo.createOrUpdate(khoa);
    }
    
}
