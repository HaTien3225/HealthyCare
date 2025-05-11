/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xhht.repositories.impl;

import com.xhht.pojo.Role;
import com.xhht.pojo.User;
import com.xhht.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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

        if (u.getId() != null) {
            User existingUser = s.get(User.class, u.getId());

            String newPassword = u.getPassword();
            if (newPassword == null || newPassword.trim().isEmpty()) {
                u.setPassword(existingUser.getPassword());
            } else if (!newPassword.startsWith("$2a$")) {
                // Nếu không bắt đầu bằng "$2a$" (bcrypt) => là mật khẩu plaintext
                u.setPassword(passwordEncoder.encode(newPassword));
            } else {
                // Nếu đã mã hóa => giữ nguyên
                u.setPassword(newPassword);
            }

            u.setRole(existingUser.getRole()); // Giữ nguyên role nếu cần
            s.merge(u);
        } else {
            // Tạo mới: luôn băm mật khẩu
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            s.persist(u);
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

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                Predicate hoPredicate = b.like(root.get("ho"), String.format("%%%s%%", kw));
                Predicate tenPredicate = b.like(root.get("ten"), String.format("%%%s%%", kw));
                predicates.add(b.or(hoPredicate, tenPredicate));
            }
            String isActive = params.get("isActive");
            if (isActive != null && !isActive.equals("null")) {
                boolean active = Boolean.parseBoolean(isActive);
                predicates.add(b.equal(root.get("isActive"), active));
            }
            String roleId = params.get("roleId");
            if (roleId != null && !roleId.isEmpty() && (roleId.equals("1") || roleId.equals("2") || roleId.equals("3"))) {
                predicates.add(b.equal(root.get("roleId").get("id").as(Integer.class), Integer.parseInt(roleId)));
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

    public List<User> findByRoleAndIsVerified(Role role, boolean isVerified) {
        // Lấy phiên làm việc của Hibernate
        Session s = this.factory.getObject().getCurrentSession();

        // Tạo câu truy vấn HQL để tìm kiếm theo role và isVerified
        Query<User> q = s.createQuery("FROM User u WHERE u.roleId = :role_id AND u.isActive = :is_active", User.class);

        // Gán tham số cho câu truy vấn
        q.setParameter("role_id", role);
        q.setParameter("is_active", isVerified);

        // Trả về danh sách người dùng
        return q.getResultList();
    }

}
