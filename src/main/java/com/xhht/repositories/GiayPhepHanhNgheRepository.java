/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.GiayPhepHanhNghe;
import java.util.Optional;

/**
 *
 * @author hatie
 */
public interface GiayPhepHanhNgheRepository {

    Optional<GiayPhepHanhNghe> findById(Integer id);

    GiayPhepHanhNghe save(GiayPhepHanhNghe old);
    
}
