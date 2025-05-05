/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.Benh;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface BenhService {
    List<Benh> getAllBenhByKhoaId(int id,int page, int pageSize);
    Benh getBenhById(int id);
}
