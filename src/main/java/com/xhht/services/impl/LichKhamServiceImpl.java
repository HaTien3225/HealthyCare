package com.xhht.services.impl;

import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import com.xhht.services.LichKhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LichKhamServiceImpl implements LichKhamService {

    @Autowired
    private LichKhamRepository lichKhamRepository;

    @Override
    public List<LichKham> getLichKhamByBacSiChuaKham(int bacSiId) {
        return lichKhamRepository.findByBacSiIdAndDaKhamFalse(bacSiId);
    }

    @Override
    public long countLichDaKham(int bacSiId) {
        return lichKhamRepository.countByBacSiIdAndDaKhamTrue(bacSiId);
    }

    @Override
    public long countLichChuaKham(int bacSiId) {
        return lichKhamRepository.countByBacSiIdAndDaKhamFalse(bacSiId);
    }

    @Override
    public Optional<LichKham> getLichKhamById(int id) {
        return lichKhamRepository.findById(id);
    }

    @Override
    public LichKham saveLichKham(LichKham lichKham) {
        return lichKhamRepository.save(lichKham);
    }

    @Override
    public List<LichKham> getLichKhamByBenhNhan(Long benhNhanId, Boolean isAccept, Boolean daKham, int page) {
        return lichKhamRepository.findByBenhNhanId(benhNhanId, isAccept, daKham, page);
    }

    @Override
    public void deleteLichKham(LichKham lichKham) {
        lichKhamRepository.delete(lichKham);
    }

    @Override
    public boolean checkTrungLichKham(LichKham lichKham) {
        return lichKhamRepository.checkLichKhamConflict(lichKham);
    }

    @Override
    public List<LichKham> getLichKhamChoDuyet(int bacSiId) {
        return lichKhamRepository.findByBacSiIdAndIsAcceptFalse(bacSiId);
    }

    @Override
    public List<LichKham> getLichKhamDaDuyetChuaKham(int bacSiId) {
        return lichKhamRepository.findByBacSiIdAndIsAcceptTrueAndDaKhamFalse(bacSiId);
    }

    @Override
    public Map<String, Long> getBenhPhoBienTheoThang(int bacSiId, Integer month) {
        return lichKhamRepository.getBenhPhoBienTheoThang(bacSiId, month);
    }

    @Override
    public Map<String, Long> getBenhPhoBienTheoQuy(int bacSiId, Integer quarter) {
        return lichKhamRepository.getBenhPhoBienTheoQuy(bacSiId, quarter);
    }
}
