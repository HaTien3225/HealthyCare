package com.xhht.services;

import com.xhht.pojo.LichKham;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LichKhamService {
    List<LichKham> getLichKhamByBacSiChuaKham(int bacSiId);
    long countLichDaKham(int bacSiId);
    long countLichChuaKham(int bacSiId);
    Optional<LichKham> getLichKhamById(int id);
    LichKham saveLichKham(LichKham lichKham);
    List<LichKham> getLichKhamByBenhNhan(Long benhNhanId, Boolean isAccept, Boolean daKham, int page);
    void deleteLichKham(LichKham lichKham);
    boolean checkTrungLichKham(LichKham lichKham);
    List<LichKham> getLichKhamChoDuyet(int bacSiId);
    List<LichKham> getLichKhamDaDuyetChuaKham(int bacSiId);
    Map<String, Long> getBenhPhoBienTheoThang(int bacSiId, Integer month);
    Map<String, Long> getBenhPhoBienTheoQuy(int bacSiId, Integer quarter);
}
