package com.xhht.repositories.impl;

import com.xhht.pojo.TinNhan;
import com.xhht.repositories.TinNhanRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TinNhanRepositoryImpl implements TinNhanRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TinNhan> findBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        Session session = sessionFactory.getCurrentSession();
        
        // Lấy cả 2 chiều hội thoại (người A gửi B hoặc B gửi A)
        Query q = session.createQuery("""
            FROM TinNhan 
            WHERE (sender.id = :senderId AND receiver.id = :receiverId)
               OR (sender.id = :receiverId AND receiver.id = :senderId)
            ORDER BY timestamp ASC
        """, TinNhan.class);
        
        q.setParameter("senderId", senderId);
        q.setParameter("receiverId", receiverId);
        
        return q.getResultList();
    }

     @Override
    public TinNhan save(TinNhan tinNhan) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(tinNhan);  // Lưu hoặc cập nhật đối tượng
        return tinNhan;
    }
    
}
