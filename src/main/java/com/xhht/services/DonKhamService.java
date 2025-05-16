/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.pojo.XetNghiem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author lehuy
 */
public interface DonKhamService {

    BigDecimal getTotalRevenue(int month, int year);

    List<DonKham> getAllDonKham(int userId, boolean isBenhNhan, int page, int pageSize, String kw, LocalDate date);

    Optional<DonKham> getDonKham(int donKhamId);

    List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId);

    
    
    BigDecimal getDonKhamPrice(int donKhamId);
    
    void updateIsPaid(int donKhamId, boolean isPaid) ;
}
