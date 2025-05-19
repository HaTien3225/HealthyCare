package com.xhht.services.impl;

import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.XetNghiemRepository;
import com.xhht.services.XetNghiemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XetNghiemServiceImpl implements XetNghiemService {

    @Autowired
    private XetNghiemRepository xetNghiemRepository;

    @Override
    public XetNghiem save(XetNghiem xetNghiem) {
        return xetNghiemRepository.save(xetNghiem);
    }

    @Override
    public List<XetNghiem> getAllXetNghiem(int donKhamId) {
        return xetNghiemRepository.getALlXetNghiem(donKhamId);
    }
}
