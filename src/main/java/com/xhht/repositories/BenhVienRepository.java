/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.BenhVien;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface BenhVienRepository {
    List<BenhVien> getBenhViens(String kw);
    BenhVien createOrUpdate(BenhVien bv);
    BenhVien getBenhVienById(int id);
    void deleteBenhVien(int id);
}
