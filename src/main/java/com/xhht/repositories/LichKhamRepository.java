/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.xhht.repositories;

import com.xhht.pojo.LichKham;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author hatie
 */
public interface LichKhamRepository {

    List<LichKham> findByBacSiIdAndDaKhamFalse(Long bacSiId);

    long countByBacSiIdAndDaKhamTrue(int bacSiId);

    long countByBacSiIdAndDaKhamFalse(int bacSiId);

    LichKham save(LichKham lichkham);

    Optional<LichKham> findById(int id);

    List<LichKham> findByBenhNhanId(Long benhNhanId, int page);

    void delete(LichKham lichKham);

    boolean checkLichKhamConflict(LichKham lichKham);

    List<LichKham> findByBacSiIdAndIsAcceptFalse(int bacSiId);

    List<LichKham> findByBacSiIdAndIsAcceptTrueAndDaKhamFalse(int bacSiId);

    Map<String, Long> getBenhPhoBienTheoThang(int bacSiId, Integer month);

    Map<String, Long> getBenhPhoBienTheoQuy(int bacSiId, Integer quarter);

}
