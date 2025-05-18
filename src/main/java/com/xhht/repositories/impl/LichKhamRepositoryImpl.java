package com.xhht.repositories.impl;

import com.xhht.pojo.HoSoSucKhoe;
import com.xhht.pojo.LichKham;
import com.xhht.repositories.LichKhamRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class LichKhamRepositoryImpl implements LichKhamRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private final int PAGE_SIZE = 10;

    @Override
    public List<LichKham> findByBacSiIdAndDaKhamFalse(Long bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("FROM LichKham WHERE bacSiId.id = :bacSiId AND daKham = false", LichKham.class);
        q.setParameter("bacSiId", bacSiId);
        return q.getResultList();
    }

    @Override
    public long countByBacSiIdAndDaKhamTrue(int bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT COUNT(l) FROM LichKham l WHERE l.bacSiId.id = :bacSiId AND l.daKham = true");
        q.setParameter("bacSiId", bacSiId);
        return (Long) q.getSingleResult();
    }

    @Override
    public long countByBacSiIdAndDaKhamFalse(int bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        Query q = session.createQuery("SELECT COUNT(l) FROM LichKham l WHERE l.bacSiId.id = :bacSiId AND l.daKham = false");
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
    public List<LichKham> findByBenhNhanId(Long benhNhanId, Boolean isAccept, Boolean daKham, int page) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "FROM LichKham WHERE benhNhanId.id = :benhNhanId "
                + "AND (:isAccept IS NULL OR isAccept = :isAccept) "
                + "AND (:daKham IS NULL OR daKham = :daKham)"
                + "ORDER BY ngay DESC";

        Query<LichKham> query = session.createQuery(hql, LichKham.class);
        query.setParameter("benhNhanId", benhNhanId);
        query.setParameter("isAccept", isAccept);
        query.setParameter("daKham", daKham);

        // Thiết lập phân trang
        int start = (page - 1) * PAGE_SIZE;
        query.setFirstResult(start);
        query.setMaxResults(PAGE_SIZE);

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

    public boolean checkLichKhamConflict(LichKham lichKham) {
        // Truy vấn HQL để kiểm tra sự trùng lặp
        String hql = "SELECT COUNT(l) FROM LichKham l "
                + "WHERE l.ngay = :ngay "
                + "AND l.khungGioId = :khungGioId "
                + "AND l.bacSiId = :bacSiId "
                + "AND l.isAccept = true "
                + // Chỉ kiểm tra các lịch đã xác nhận
                "AND l.daKham = false";    // Lịch chưa khám

        Query<Long> query = sessionFactory.getCurrentSession().createQuery(hql, Long.class);
        query.setParameter("ngay", lichKham.getNgay());
        query.setParameter("khungGioId", lichKham.getKhungGio());
        query.setParameter("bacSiId", lichKham.getBacSiId());

        Long count = query.uniqueResult();

        // Nếu count > 0 thì có lịch khám trùng
        return count == 0; // Trả về true nếu không có lịch trùng, false nếu có
    }

    @Override
    public List<LichKham> findByBacSiIdAndIsAcceptFalse(int bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM LichKham l WHERE l.bacSiId.id = :bacSiId AND l.isAccept = false";
        Query<LichKham> query = session.createQuery(hql, LichKham.class);
        query.setParameter("bacSiId", bacSiId);
        return query.getResultList();
    }

    @Override
    public List<LichKham> findByBacSiIdAndIsAcceptTrueAndDaKhamFalse(int bacSiId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM LichKham l WHERE l.bacSiId.id = :bacSiId AND l.isAccept = true AND l.daKham=false";
        Query<LichKham> query = session.createQuery(hql, LichKham.class);
        query.setParameter("bacSiId", bacSiId);
        return query.getResultList();
    }

    @Override
    public Map<String, Long> getBenhPhoBienTheoThang(int bacSiId, Integer month) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT d.benhId.tenBenh, COUNT(d.id) "
                + "FROM DonKham d "
                + "JOIN d.lichKhamId lk "
                + "WHERE MONTH(lk.ngay) = :month AND lk.bacSiId.id = :bacSiId AND lk.daKham = true "
                + "GROUP BY d.benhId.tenBenh";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("month", month);
        query.setParameter("bacSiId", bacSiId);

        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new java.util.HashMap<>();
        for (Object[] row : results) {
            String tenBenh = (String) row[0];
            Long count = (Long) row[1];
            resultMap.put(tenBenh, count);
        }

        return resultMap;
    }

    @Override
    public Map<String, Long> getBenhPhoBienTheoQuy(int bacSiId, Integer quarter) {
        Session session = sessionFactory.getCurrentSession();

        // Tính khoảng tháng của quý
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = startMonth + 2;

        String hql = "SELECT d.benhId.tenBenh, COUNT(d.id) "
                + "FROM DonKham d "
                + "JOIN d.lichKhamId lk "
                + "WHERE MONTH(lk.ngay) BETWEEN :startMonth AND :endMonth "
                + "AND lk.bacSiId.id = :bacSiId AND lk.daKham = true "
                + "GROUP BY d.benhId.tenBenh";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("startMonth", startMonth);
        query.setParameter("endMonth", endMonth);
        query.setParameter("bacSiId", bacSiId);

        List<Object[]> results = query.getResultList();

        Map<String, Long> resultMap = new java.util.HashMap<>();
        for (Object[] row : results) {
            String tenBenh = (String) row[0];
            Long count = (Long) row[1];
            resultMap.put(tenBenh, count);
        }

        return resultMap;
    }

}
