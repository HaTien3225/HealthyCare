/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.Khoa;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface KhoaRepository {
    List<Khoa> getAllKhoaByBenhVienId(int benhVienId, int page, int pageSize);
    Khoa getKhoaByKhoaId(int KhoaId);
}
