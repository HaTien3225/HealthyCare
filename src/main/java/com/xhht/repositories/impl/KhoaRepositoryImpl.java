/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.Khoa;
import com.xhht.repositories.KhoaRepository;
import java.util.List;
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
public class KhoaRepositoryImpl implements KhoaRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Khoa> getAllKhoaByBenhVienId(int benhVienId, int page, int pageSize) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Khoa> query = s.createQuery("SELECT k  FROM Khoa k WHERE k.benhVienId.id = :benhVienId", Khoa.class);
        query.setParameter("benhVienId", benhVienId);

        // Thêm phân trang
        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public Khoa getKhoaByKhoaId(int KhoaId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
