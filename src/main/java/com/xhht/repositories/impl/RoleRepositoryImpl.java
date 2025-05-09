/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.Role;
import com.xhht.repositories.RoleRepository;
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
public class RoleRepositoryImpl implements RoleRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Role> getAllRole() {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Role> q = s.createQuery("FROM Role",Role.class);
        return q.getResultList();
    }

    @Override
    public Role getRoleById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Role> q = s.createQuery("FROM Role WHERE id = :id",Role.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }
}
