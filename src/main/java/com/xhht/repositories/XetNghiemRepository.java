/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.XetNghiem;
import java.util.List;

/**
 *
 * @author hatie
 */
public interface XetNghiemRepository {
    XetNghiem save(XetNghiem xetNghiem);
    List<XetNghiem> getALlXetNghiem(int donKhamId);
}
