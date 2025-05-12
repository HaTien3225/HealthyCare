package com.xhht.repositories.impl;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.repositories.HoSoSucKhoeRepository;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HoSoSucKhoeRepositoryImpl implements HoSoSucKhoeRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HoSoSucKhoeRepository hoSoSucKhoeRepository;

    @Override
    public Optional<HoSoSucKhoe> findByBenhNhanId(int benhNhanId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM HoSoSucKhoe WHERE benhNhan.id = :id", HoSoSucKhoe.class);
        q.setParameter("id", benhNhanId);

        try {
            HoSoSucKhoe result = (HoSoSucKhoe) q.getSingleResult();
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public HoSoSucKhoe save(HoSoSucKhoe hoSo) {
        return hoSoSucKhoeRepository.save(hoSo);
    }

    @Override
    public void delete(HoSoSucKhoe hoSo) {
        Session session = sessionFactory.getCurrentSession();

        Optional<HoSoSucKhoe> optional = this.findByBenhNhanId(hoSo.getBenhNhanId().getId());

        if (optional.isPresent()) {
            session.delete(optional.get());
        } else {
            System.out.println("Không tìm thấy hồ sơ sức khỏe để xóa"); // hoặc xử lý log khác
        }
    }

}
