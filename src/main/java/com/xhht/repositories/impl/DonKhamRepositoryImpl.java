/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.repositories.DonKhamRepository;
import java.math.BigDecimal;
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
public class DonKhamRepositoryImpl implements DonKhamRepository{

    @Autowired
     private LocalSessionFactoryBean factory; 
    
    @Override
    public BigDecimal getTotalRevenue(int month, int year) {
    Session s = this.factory.getObject().getCurrentSession();

    String hql = """
        SELECT COALESCE(SUM(ct.giaTien), 0)
        FROM ChiTietDonKham ct
        JOIN ct.donKhamId dk
        WHERE MONTH(dk.createdDate) = :month AND YEAR(dk.createdDate) = :year
    """;

    Query<BigDecimal> query = s.createQuery(hql, BigDecimal.class);
    query.setParameter("month", month);
    query.setParameter("year", year);

    return query.getSingleResult();
}

    
}
