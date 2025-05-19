/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.repositories.HoSoSucKhoeRepository;
import com.xhht.services.HoSoSucKhoeService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hatie
 */
@Service
public class HoSoSucKhoeServiceImpl implements HoSoSucKhoeService {

    @Autowired
    private HoSoSucKhoeRepository hoSoSucKhoeRepository;

    @Override
    public Optional<HoSoSucKhoe> getHoSoByBenhNhanId(int benhNhanId) {
        return hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
    }

    @Override
    public HoSoSucKhoe saveOrUpdate(HoSoSucKhoe hoSo) {
        return hoSoSucKhoeRepository.createOrUpdate(hoSo);
    }

    @Override
    public void deleteHoSo(int benhNhanId) {
        Optional<HoSoSucKhoe> hoSo = hoSoSucKhoeRepository.findByBenhNhanId(benhNhanId);
        hoSo.ifPresent(hoSoSucKhoeRepository::delete);
    }
}
