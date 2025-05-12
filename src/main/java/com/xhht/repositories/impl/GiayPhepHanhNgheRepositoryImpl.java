/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.repositories.GiayPhepHanhNgheRepository;
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
public class GiayPhepHanhNgheRepositoryImpl implements GiayPhepHanhNgheRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void updateGiayPhepStatus(int id, Boolean isActive) {
        Session session = factory.getObject().getCurrentSession();
        Query query = session.createQuery("UPDATE GiayPhepHanhNghe g SET g.isValid = :status WHERE g.id = :id");
        query.setParameter("status", isActive);
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
