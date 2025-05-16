/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.DonKham;
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
}
