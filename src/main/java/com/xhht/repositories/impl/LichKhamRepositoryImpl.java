package com.xhht.repositories.impl;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LichKhamRepositoryImpl implements LichKhamRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<LichKham> findByBacSiIdAndDaKhamFalse(Long bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM LichKham WHERE bacSi.id = :bacSiId AND daKham = false", LichKham.class);
        q.setParameter("bacSiId", bacSiId);
        return q.getResultList();
    }

    @Override
    public long countByBacSiIdAndDaKhamTrue(Long bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT COUNT(l) FROM LichKham l WHERE l.bacSi.id = :bacSiId AND l.daKham = true");
        q.setParameter("bacSiId", bacSiId);
        return (Long) q.getSingleResult();
    }

    @Override
    public long countByBacSiIdAndDaKhamFalse(Long bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT COUNT(l) FROM LichKham l WHERE l.bacSi.id = :bacSiId AND l.daKham = false");
        q.setParameter("bacSiId", bacSiId);
        return (Long) q.getSingleResult();
    }

    @Override
    public Optional<LichKham> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        LichKham lichKham = session.get(LichKham.class, id);
        return Optional.ofNullable(lichKham);
    }

    @Override
    public LichKham save(LichKham lichkham) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(lichkham);
        return lichkham;
    }

    @Override
    public List<LichKham> findByBenhNhanId(Long benhNhanId) {
        Session session = sessionFactory.getCurrentSession();
        Query<LichKham> query = session.createQuery("FROM LichKham WHERE benhNhan.id = :benhNhanId", LichKham.class);
        query.setParameter("benhNhanId", benhNhanId);
        return query.getResultList();
    }

    @Override
    public void delete(LichKham lichKham) {
         Session session = sessionFactory.getCurrentSession();

        Optional<LichKham> optional = this.findById(lichKham.getId());

        if (optional.isPresent()) {
            session.delete(optional.get());
        } else {
            System.out.println("Không tìm thấy hồ sơ sức khỏe để xóa"); // hoặc xử lý log khác
        }
    }

}
