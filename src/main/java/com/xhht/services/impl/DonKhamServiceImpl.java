package com.xhht.services.impl;

import com.xhht.pojo.Benh;
import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.repositories.DonKhamRepository;
import com.xhht.services.DonKhamService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonKhamServiceImpl implements DonKhamService {

    @Autowired
    private DonKhamRepository donKhamRepository;

    @Override
    public BigDecimal getTotalRevenue(int month, int year) {
        return donKhamRepository.getTotalRevenue(month, year);
    }

    @Override
    public List<DonKham> getAllDonKham(int userId, boolean isBenhNhan, int page, int pageSize, String kw, LocalDate date, Boolean isPaid) {
        return donKhamRepository.getAllDonKham(userId, isBenhNhan, page, pageSize, kw, date, isPaid);
    }

    @Override
    public Optional<DonKham> getDonKham(int donKhamId) {
        return donKhamRepository.getDonKham(donKhamId);
    }

    @Override
    public List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId) {
        return donKhamRepository.getAllChiTietDonKham(donKhamId);
    }

    @Override
    public BigDecimal getDonKhamPrice(int donKhamId) {
        return donKhamRepository.getDonKhamPrice(donKhamId);
    }

    @Override
    public void updateIsPaid(int donKhamId, boolean isPaid) {
        donKhamRepository.updateIsPaid(donKhamId, isPaid);
    }

    @Override
    public DonKham save(DonKham donKham) {
        return donKhamRepository.save(donKham);
    }

    @Override
    public Optional<Benh> getBenhByDonKham(Long donKhamId) {
        return donKhamRepository.getBenhByDonKham(donKhamId);
    }
}
