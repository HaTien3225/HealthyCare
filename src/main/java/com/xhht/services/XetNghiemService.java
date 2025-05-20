package com.xhht.services;

import com.xhht.pojo.XetNghiem;
import java.util.List;

public interface XetNghiemService {
    XetNghiem save(XetNghiem xetNghiem);
    List<XetNghiem> getAllXetNghiem(int donKhamId);
    void saveAll(List<XetNghiem> xetNghiemList);
}
