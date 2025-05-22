/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.DanhGia;
import com.xhht.repositories.DanhGiaRepository;
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
public class DanhGiaRepositoryImpl implements DanhGiaRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<DanhGia> getAllDanhGia(Integer benhNhanId, Integer bacSiId, Boolean isBinhLuan, Boolean isPhanHoi) {

        Session session = this.sessionFactory.getObject().getCurrentSession();

        StringBuilder hql = new StringBuilder("FROM DanhGia d WHERE 1=1");

        if (benhNhanId != null) {
            hql.append(" AND d.benhNhanId.id = :benhNhanId");
        }
        if (bacSiId != null) {
            hql.append(" AND d.bacSiId.id = :bacSiId");
        }
        if (isBinhLuan != null) {
            if (isBinhLuan) {
                hql.append(" AND d.binhLuan IS NOT NULL AND d.binhLuan <> ''");
            } else {
                hql.append(" AND (d.binhLuan IS NULL OR d.binhLuan = '')");
            }
        }
        if (isPhanHoi != null) {
            if (isPhanHoi) {
                hql.append(" AND d.phanHoi IS NOT NULL AND d.phanHoi <> ''");
            } else {
                hql.append(" AND (d.phanHoi IS NULL OR d.phanHoi = '')");
            }
        }

        Query<DanhGia> query = session.createQuery(hql.toString(), DanhGia.class);

        if (benhNhanId != null) {
            query.setParameter("benhNhanId", benhNhanId);
        }
        if (bacSiId != null) {
            query.setParameter("bacSiId", bacSiId);
        }

        return query.getResultList();

    }

    @Override
    public DanhGia getDanhGiaById(Integer danhGiaId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query<DanhGia> query = s.createQuery("FROM DanhGia WHERE id = :id", DanhGia.class);
        query.setParameter("id", danhGiaId);
        return query.getSingleResult();
    }

    @Override
    public DanhGia createOrUpdate(DanhGia danhGia) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if (danhGia.getId() == null) {
            s.persist(danhGia);
        } else {
            s.merge(danhGia);
        }
        return danhGia;
    }

}
