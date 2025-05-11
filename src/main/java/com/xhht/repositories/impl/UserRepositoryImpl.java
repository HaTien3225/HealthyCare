/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.BenhVien;
import com.xhht.pojo.Khoa;
import com.xhht.pojo.User;
import com.xhht.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lehuy
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session session = this.factory.getObject().openSession();
        try {
            Query<User> q = session.createQuery(
                    "SELECT u FROM User u LEFT JOIN FETCH u.roleId WHERE u.username = :username",
                    User.class
            );
            q.setParameter("username", username);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public User createOrUpdate(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        if (u.getId() == null) {
            s.persist(u);
        } else {
            s.merge(u);
        }
        return u;
    }

    @Override
    public List<User> getAllUser(Map<String, String> params) {

        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);

        Join<User, Khoa> khoaJoin = root.join("khoaId", JoinType.LEFT);
        Join<Khoa, BenhVien> benhVienJoin = khoaJoin.join("benhVienId", JoinType.LEFT);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            

            String isActive = params.get("isActive");
            if (isActive != null && !isActive.equals("null")) {
                boolean active = Boolean.parseBoolean(isActive);
                predicates.add(b.equal(root.get("isActive"), active));
            }
            String roleId = params.get("roleId");
            if (roleId != null && !roleId.isEmpty() && (roleId.equals("1") || roleId.equals("2") || roleId.equals("3"))) {
                predicates.add(b.equal(root.get("roleId").get("id").as(Integer.class), Integer.parseInt(roleId)));
            }
            String khoaid = params.get("khoaid");
            if (khoaid != null && !khoaid.isEmpty()) {
                predicates.add(b.equal(root.get("khoaId").get("id"), Integer.parseInt(khoaid)));
            }
            String benhvienid = params.get("benhvienid");
            if (benhvienid != null && !benhvienid.isEmpty()) {
                predicates.add(b.equal(benhVienJoin.get("id"), Integer.parseInt(benhvienid)));
            }
            
            String kw = params.get("kw");
            if (kw != null && !kw.trim().isEmpty()) {
                String pattern = "%" + kw.toLowerCase() + "%";
                Expression<String> hoExp = b.lower(root.get("ho"));
                Expression<String> tenExp = b.lower(root.get("ten"));
                Predicate hoPredicate = b.like(hoExp, pattern);
                Predicate tenPredicate = b.like(tenExp, pattern);
                predicates.add(b.or(hoPredicate, tenPredicate));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int start = (page - 1) * 10;
            query.setMaxResults(10);
            query.setFirstResult(start);
        }

        return query.getResultList();

    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<User> q = s.createQuery("FROM User WHERE id = :id", User.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

}
