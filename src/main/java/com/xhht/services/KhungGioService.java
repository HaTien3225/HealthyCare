/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.KhungGio;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface KhungGioService {
    KhungGio findKhungGioById(int id);
    List<KhungGio> findKhungGioDaDatCuaBacSiTrongNgay(int bacSiId, LocalDate ngay);
    List<KhungGio> getAllKhungGio();
}
