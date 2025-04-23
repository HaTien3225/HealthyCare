/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.BenhVien;
import java.util.List;



/**
 *
 * @author lehuy
 */
public interface BenhVienService{
    List<BenhVien> getBenhViens(String kw);
    BenhVien createOrUpdate(BenhVien bv);
}
