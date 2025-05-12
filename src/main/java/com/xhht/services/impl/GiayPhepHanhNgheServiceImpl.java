/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.xhht.pojo.GiayPhepHanhNghe;
import com.xhht.pojo.User;
import com.xhht.repositories.GiayPhepHanhNgheRepository;
import com.xhht.services.GiayPhepHanhNgheService;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.xhht.repositories.GiayPhepHanhNgheRepository;
import com.xhht.services.GiayPhepHanhNgheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiayPhepHanhNgheServiceImpl implements GiayPhepHanhNgheService {

    @Autowired
    private GiayPhepHanhNgheRepository giayPhepRepo;

    @Override
    public GiayPhepHanhNghe createOrUpdate(GiayPhepHanhNghe giayPhep) {
        if (giayPhep.getId() != null) {
            Optional<GiayPhepHanhNghe> existing = giayPhepRepo.findById(giayPhep.getId());
            if (existing.isPresent()) {
                GiayPhepHanhNghe old = existing.get();
                old.setImage(giayPhep.getImage());
                return giayPhepRepo.save(old);
            }
        }
        
        return giayPhepRepo.save(giayPhep);
    }
    @Override
    public void updateGiayPhepStatus(int id, Boolean isActive) {
        giayPhepRepo.updateGiayPhepStatus(id, isActive);
    }
}



