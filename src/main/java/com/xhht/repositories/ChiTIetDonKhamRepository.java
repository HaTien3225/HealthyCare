/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.ChiTietDonKham;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface ChiTIetDonKhamRepository {
    void saveAll(List<ChiTietDonKham> chiTietList);
}
