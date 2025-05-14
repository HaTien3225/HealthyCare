/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.LichKham;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author hatie
 */
public interface LichKhamRepository {

    List<LichKham> findByBacSiIdAndDaKhamFalse(Long bacSiId);

    long countByBacSiIdAndDaKhamTrue(Long bacSiId);

    long countByBacSiIdAndDaKhamFalse(Long bacSiId);

    LichKham save(LichKham lichkham);

    Optional<LichKham> findById(int id);

    List<LichKham> findByBenhNhanId(Long benhNhanId,int page);

    void delete(LichKham lichKham);
    
    boolean checkLichKhamConflict(LichKham lichKham);
}
