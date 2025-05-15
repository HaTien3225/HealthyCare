/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.KhungGio;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.xhht.repositories.KhungGioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author lehuy
 */
@Repository
@Transactional
public class KhungGioRepositoryImpl implements KhungGioRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public KhungGio findKhungGioById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<KhungGio> q = s.createQuery("FROM KhungGio WHERE id = :id",KhungGio.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public List<KhungGio> findKhungGioDaDatCuaBacSiTrongNgay(int bacSiId, LocalDate ngay) {
       Session session = this.factory.getObject().getCurrentSession();

        String hql = """
            SELECT lk.khungGioId
            FROM LichKham lk
            WHERE lk.bacSiId.id = :bacSiId
              AND lk.ngay = :ngay
              AND lk.isAccept = true
              AND lk.daKham = false
        """;

        Query<KhungGio> query = session.createQuery(hql, KhungGio.class);
        query.setParameter("bacSiId", bacSiId);
        query.setParameter("ngay", ngay);
        

        return query.getResultList();
    }

    @Override
    public List<KhungGio> getAllKhungGio() {
        
        Session s = this.factory.getObject().getCurrentSession();
        Query<KhungGio> q = s.createQuery("FROM KhungGio",KhungGio.class);
        return q.getResultList();
    }
    
}
