package com.xhht.services.impl;

import com.xhht.pojo.Benh;
import com.xhht.repositories.BenhRepository;
import com.xhht.services.BenhService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenhServiceImpl implements BenhService {

    @Autowired
    private BenhRepository benhRepository;

    @Override
    public List<Benh> getAllBenhByKhoaId(int khoaId, int page, int pageSize) {
        return benhRepository.getAllBenhByKhoaId(khoaId, page, pageSize);
    }

    @Override
    public Benh getBenhById(int id) {
        return benhRepository.getBenhById(id);
    }

    @Override
    public Benh save(Benh benh) {
        return benhRepository.save(benh);
    }

    @Override
    public List<Benh> findByTenBenh(String keyword) {
        return benhRepository.findByTenBenh(keyword);
    }
}
