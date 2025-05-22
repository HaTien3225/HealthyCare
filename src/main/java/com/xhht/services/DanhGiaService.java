/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.services;

import com.xhht.pojo.DanhGia;
import java.util.List;

/**
 *
 * @author lehuy
 */
public interface DanhGiaService {
    List<DanhGia> getAllDanhGia(Integer benhNhanId, Integer bacSiId,Boolean isBinhLuan,Boolean isPhanHoi);
    DanhGia getDanhGiaById(Integer danhGiaId);
    DanhGia createOrUpdate(DanhGia danhGia);
}
