package com.xhht.repositories.impl;

import com.xhht.pojo.GiayPhepHanhNghe;
import com.xhht.repositories.GiayPhepHanhNgheRepository;
import java.util.Optional;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GiayPhepHanhNgheRepositoryImpl implements GiayPhepHanhNgheRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Optional<GiayPhepHanhNghe> findById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        GiayPhepHanhNghe g = s.get(GiayPhepHanhNghe.class, id);
        return Optional.ofNullable(g);
    }

    @Override
    public GiayPhepHanhNghe save(GiayPhepHanhNghe g) {
        Session s = this.factory.getObject().getCurrentSession();
        if (g.getId() == null) {
            s.persist(g);
        } else {
            g = (GiayPhepHanhNghe) s.merge(g);
        }
        return g;
    }
}
