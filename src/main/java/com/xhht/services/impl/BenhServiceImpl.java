
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.Benh;
import com.xhht.repositories.BenhRepository;
import com.xhht.services.BenhService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class BenhServiceImpl implements BenhService{
    @Autowired
    private BenhRepository benhRepo;

    @Override
    public List<Benh> getAllBenhByKhoaId(int id, int page, int pageSize) {
        return this.benhRepo.getAllBenhByKhoaId(id, page, pageSize);
    }

    @Override
    public Benh getBenhById(int id) {
        return this.benhRepo.getBenhById(id);
    }
}
