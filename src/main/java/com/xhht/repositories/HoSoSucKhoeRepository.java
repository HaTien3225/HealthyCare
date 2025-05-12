/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.HoSoSucKhoe;
import java.util.Optional;

/**
 *
 * @author hatie
 */
public interface HoSoSucKhoeRepository {
    Optional<HoSoSucKhoe> findByBenhNhanId(int benhNhanId);

    HoSoSucKhoe save(HoSoSucKhoe hoSo);

    void delete(HoSoSucKhoe get);
}
