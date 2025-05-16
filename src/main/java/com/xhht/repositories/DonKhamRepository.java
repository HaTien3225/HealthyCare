/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.pojo.XetNghiem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface DonKhamRepository {
    BigDecimal getTotalRevenue(int month, int year);
    List<DonKham> getAllDonKham(int userId,boolean isBenhNhan,int page,int pageSize,String kw,LocalDate date);
    DonKham getDonKham(int donKhamId);
    List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId);
    List<XetNghiem> getALlXetNghiem(int donKhamId);
}
