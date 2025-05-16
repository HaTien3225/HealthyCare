/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services.impl;

import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.DonKhamRepository;
import com.xhht.services.DonKhamService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lehuy
 */
@Service
public class DonKhamServiceImpl implements DonKhamService {

    @Autowired
    DonKhamRepository donKhamRepo;

    @Override
    public BigDecimal getTotalRevenue(int month, int year) {

        return this.donKhamRepo.getTotalRevenue(month, year);
    }

    @Override
    public List<DonKham> getAllDonKham(int userId, boolean isBenhNhan, int page, int pageSize, String kw, LocalDate date) {
        return this.donKhamRepo.getAllDonKham(userId, isBenhNhan, page, pageSize, kw, date);
    }

    @Override
    public DonKham getDonKham(int donKhamId) {
        return this.donKhamRepo.getDonKham(donKhamId);
    }

    @Override
    public List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId) {
        return this.donKhamRepo.getAllChiTietDonKham(donKhamId);
    }

    @Override
    public List<XetNghiem> getALlXetNghiem(int donKhamId) {
        return this.donKhamRepo.getALlXetNghiem(donKhamId);
    }

    @Override
    public BigDecimal getDonKhamPrice(int donKhamId) {
        return this.donKhamRepo.getDonKhamPrice(donKhamId);
    }

    @Override
    public void updateIsPaid(int donKhamId, boolean isPaid) {
        this.donKhamRepo.updateIsPaid(donKhamId, isPaid);
    }

}
