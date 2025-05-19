package com.xhht.services;

import com.xhht.pojo.Benh;
import java.util.List;

public interface BenhService {
    List<Benh> getAllBenhByKhoaId(int khoaId, int page, int pageSize);
    Benh getBenhById(int id);
    Benh save(Benh benh);
    List<Benh> findByTenBenh(String keyword);
}
