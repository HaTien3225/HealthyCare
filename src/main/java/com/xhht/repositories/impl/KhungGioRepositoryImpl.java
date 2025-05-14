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
    
}
