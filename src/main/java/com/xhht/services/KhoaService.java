/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.Khoa;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface KhoaService {

    List<Khoa> getAllKhoaByBenhVienId(int benhVienId, int page, int pageSize);

    Khoa getKhoaByKhoaId(int KhoaId);
    
    Khoa createOrUpdate(Khoa khoa);
    
    void deleteKhoa(int id);
    
}
