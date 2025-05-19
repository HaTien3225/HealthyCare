package com.xhht.services;

import com.xhht.pojo.Benh;
import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonKhamService {
    BigDecimal getTotalRevenue(int month, int year);

    List<DonKham> getAllDonKham(int userId, boolean isBenhNhan, int page, int pageSize, String kw, LocalDate date, Boolean isPaid);

    Optional<DonKham> getDonKham(int donKhamId);

    List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId);

    BigDecimal getDonKhamPrice(int donKhamId);

    void updateIsPaid(int donKhamId, boolean isPaid);

    DonKham save(DonKham donKham);

    Optional<Benh> getBenhByDonKham(Long donKhamId);
}
