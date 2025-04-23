/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.BenhVien;
import com.xhht.repositories.BenhVienRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
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
public class BenhVienRepositoryImpl implements BenhVienRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<BenhVien> getBenhViens(String kw) {
        Session s = this.factory.getObject().getCurrentSession();
        if (kw != null && !kw.isEmpty()) {
            Query query = s.createQuery("FROM BenhVien WHERE tenBenhVien LIKE :kw", BenhVien.class);
            query.setParameter("kw", "%" + kw + "%");
            return query.getResultList();
        } else {
            Query query = s.createQuery("FROM BenhVien", BenhVien.class);
            return query.getResultList();
        }       
    }

    @Override
    public BenhVien createOrUpdate(BenhVien bv) {
       Session s = this.factory.getObject().getCurrentSession();
       if (bv.getId() != null){
           s.persist(bv);
       }else{
           s.merge(bv);
       }
       return bv;
    }

}
