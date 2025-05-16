/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.Benh;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface BenhRepository {
    List<Benh> getAllBenhByKhoaId(int id,int page, int pageSize);
    Benh getBenhById(int id);
    Benh save(Benh benh);
    List<Benh> findByTenBenh(String keyword);

}
