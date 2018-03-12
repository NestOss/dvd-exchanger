package com.nestos.dvdexchanger.repository.user;

import com.nestos.dvdexchanger.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public User fetchOne(Long id) {
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("from User as user left join fetch user.disks where id = :paramId");
        query.setParameter("paramId", id);
        return (User) query.uniqueResult();
    }

}
