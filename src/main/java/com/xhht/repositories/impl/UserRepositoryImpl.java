/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.User;
import com.xhht.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public User getUserByUsername(String username) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            String hql = "FROM User WHERE username = :username";
            return session.createQuery(hql, User.class)
                          .setParameter("username", username)
                          .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        
        return u;
    }
}
