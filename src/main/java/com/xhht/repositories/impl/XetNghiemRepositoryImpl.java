/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.XetNghiem;
import com.xhht.repositories.XetNghiemRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hatie
 */
@Repository
@Transactional
public class XetNghiemRepositoryImpl implements XetNghiemRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public XetNghiem save(XetNghiem xetNghiem) {
        Session session = this.factory.getObject().getCurrentSession();
        session.saveOrUpdate(xetNghiem);
        return xetNghiem;
    }
    @Override
    public List<XetNghiem> getALlXetNghiem(int donKhamId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<XetNghiem> q = session.createQuery("FROM XetNghiem c WHERE c.donKhamId.id = :donKhamId", XetNghiem.class);
        q.setParameter("donKhamId", donKhamId);
        return q.getResultList();
    }
    

    @Override
    public void saveAll(List<XetNghiem> xetNghiemList) {
        Session session = this.factory.getObject().getCurrentSession();
        for (XetNghiem xn : xetNghiemList) {
            session.save(xn); // hoặc saveOrUpdate nếu có id
        }
    }
}
