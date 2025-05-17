/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.Benh;
import com.xhht.pojo.ChiTietDonKham;
import com.xhht.pojo.DonKham;
import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.DonKhamRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lehuy
 */
@Repository
@Transactional
public class DonKhamRepositoryImpl implements DonKhamRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public BigDecimal getTotalRevenue(int month, int year) {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = """
        SELECT COALESCE(SUM(ct.giaTien), 0)
        FROM ChiTietDonKham ct
        JOIN ct.donKhamId dk
        WHERE MONTH(dk.createdDate) = :month AND YEAR(dk.createdDate) = :year AND dk.isPaid = true
    """;

        Query<BigDecimal> query = s.createQuery(hql, BigDecimal.class);
        query.setParameter("month", month);
        query.setParameter("year", year);

        return query.getSingleResult();
    }

    @Override
    public List<DonKham> getAllDonKham(int userId, boolean isBenhNhan, int page, int pageSize, String kw, LocalDate date,Boolean isPaid) {
        Session session = this.factory.getObject().getCurrentSession();

        StringBuilder hql = new StringBuilder("FROM DonKham dk WHERE ");

        // Điều kiện theo userId
        if (isBenhNhan) {
            hql.append("dk.hoSoSucKhoeId.benhNhanId.id = :userId");
        } else {
            hql.append("dk.bacSiId.id = :userId");
        }

        // Điều kiện tìm kiếm theo từ khóa
        if (kw != null && !kw.trim().isEmpty()) {
            hql.append(" AND dk.ghiChu LIKE :kw");
        }

        // Điều kiện theo ngày
        if (date != null) {
            hql.append(" AND dk.createdDate = :date");
        }
        
        if (isPaid != null) {
            hql.append(" AND dk.isPaid = :isPaid");
        }

        hql.append(" ORDER BY dk.createdDate DESC");

        Query<DonKham> query = session.createQuery(hql.toString(), DonKham.class);
        query.setParameter("userId", userId);

        if (kw != null && !kw.trim().isEmpty()) {
            query.setParameter("kw", "%" + kw + "%");
        }

        if (date != null) {
            query.setParameter("date", date);
        }
        if(isPaid != null)
            query.setParameter("isPaid", isPaid);

        // Phân trang
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public Optional<DonKham> getDonKham(int donKhamId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<DonKham> q = session.createQuery("FROM DonKham WHERE id = :id", DonKham.class);
        q.setParameter("id", donKhamId);
        DonKham result = q.uniqueResult();
        return Optional.ofNullable(result);
    }

    @Override
    public List<ChiTietDonKham> getAllChiTietDonKham(int donKhamId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<ChiTietDonKham> q = session.createQuery("FROM ChiTietDonKham c WHERE c.donKhamId.id = :donKhamId", ChiTietDonKham.class);
        q.setParameter("donKhamId", donKhamId);
        return q.getResultList();
    }

    @Override
    public BigDecimal getDonKhamPrice(int donKhamId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT SUM(c.giaTien) FROM ChiTietDonKham c WHERE c.donKhamId.id = :donKhamId";
        Query<BigDecimal> query = session.createQuery(hql, BigDecimal.class);
        query.setParameter("donKhamId", donKhamId);

        BigDecimal total = query.uniqueResult();
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void updateIsPaid(int donKhamId, boolean isPaid) {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createQuery("UPDATE DonKham SET isPaid = :isPaid WHERE id = :id");
        q.setParameter("isPaid", isPaid);
        q.setParameter("id", donKhamId);
        int updated = q.executeUpdate();

        if (updated == 0) {
            throw new EntityNotFoundException("Không tìm thấy DonKham với id = " + donKhamId);
        }
    }

    @Override
    public DonKham save(DonKham donKham) {
        Session session = this.factory.getObject().getCurrentSession();
        session.saveOrUpdate(donKham);
        return donKham;
    }

    @Override
    public Optional<Benh> getBenhByDonKham(Long donKhamId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Benh> query = session.createQuery(
                "SELECT d.benhId FROM DonKham d WHERE d.id = :donKhamId", Benh.class);
        query.setParameter("donKhamId", donKhamId);

        List<Benh> result = query.getResultList();
        if (result.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(result.get(0));
        }
    }

}
