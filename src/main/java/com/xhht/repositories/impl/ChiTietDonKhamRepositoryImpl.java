/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.ChiTietDonKham;
import com.xhht.repositories.ChiTIetDonKhamRepository;
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
public class ChiTietDonKhamRepositoryImpl implements ChiTIetDonKhamRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveAll(List<ChiTietDonKham> chiTietList) {
        Session s = this.factory.getObject().getCurrentSession();
        for (ChiTietDonKham chiTiet : chiTietList) {
            s.save(chiTiet);  
        }
    }

}
