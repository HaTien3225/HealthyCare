 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.BenhVien;
import com.xhht.repositories.BenhVienRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xhht.services.BenhVlenService;

/**
 *
 * @author lehuy
 */
@Service
public class BenhVienServiceImpl implements BenhVlenService{

    @Autowired
    private BenhVienRepository benhVienRepo;
    
    @Override
    public List<BenhVien> getBenhViens(String kw) {
        return this.benhVienRepo.getBenhViens(kw);
    }

    @Override
    public BenhVien createOrUpdate(BenhVien bv) {
        return this.benhVienRepo.createOrUpdate(bv);
    }

    @Override
    public BenhVien getBenhVienById(int id) {
        return this.benhVienRepo.getBenhVienById(id);
    }
    
}
