/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.Benh;
import com.xhht.pojo.Khoa;
import com.xhht.repositories.BenhRepository;
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
public class BenhRepositoryImpl implements BenhRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Benh> getAllBenhByKhoaId(int khoaId, int page, int pageSize) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Benh> query = s.createQuery("SELECT b  FROM Benh b WHERE b.khoaId.id = :khoaId", Benh.class);
        query.setParameter("khoaId", khoaId);

        // Thêm phân trang
        int start = (page - 1) * pageSize;
        query.setFirstResult(start);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public Benh getBenhById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Benh> query = s.createQuery("SELECT b  FROM Benh b WHERE b.id = :id", Benh.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Benh save(Benh benh) {
        Session session = this.factory.getObject().getCurrentSession();
        session.saveOrUpdate(benh);
        return benh;
    }

    @Override
    public List<Benh> findByTenBenh(String keyword) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Benh> query = s.createQuery("FROM Benh b WHERE LOWER(b.tenBenh) LIKE :kw", Benh.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

}
