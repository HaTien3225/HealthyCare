/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.BenhVien;
import com.xhht.repositories.BenhVienRepository;
import com.xhht.services.BenhVIenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class BenhVienServiceImpl implements BenhVIenService{

    @Autowired
    private BenhVienRepository benhVienRepo;
    
    @Override
    public List<BenhVien> getBenhViens() {
        return this.benhVienRepo.getBenhViens();
    }
    
}
