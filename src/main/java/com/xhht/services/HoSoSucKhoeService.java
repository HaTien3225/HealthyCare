package com.xhht.services;

import com.xhht.pojo.HoSoSucKhoe;
import java.util.Optional;

public interface HoSoSucKhoeService {
    Optional<HoSoSucKhoe> getHoSoByBenhNhanId(int benhNhanId);
    HoSoSucKhoe saveOrUpdate(HoSoSucKhoe hoSo);
    void deleteHoSo(int benhNhanId);
}
