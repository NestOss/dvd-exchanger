package com.nestos.dvdexchanger.repository.disk;


import com.nestos.dvdexchanger.entity.Disk;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DiskRepositoryImpl implements DiscRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    /**
     * Возвращает список незанятых дисков
     *
     * @return список незанятых дисков
     */
    @Override
    public List<Disk> findFreeDisks() {
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery(
                "select disk from TakenItem as takenItem "
                        + "right join takenItem.disk as disk "
                        + "where takenItem.disk is null");
        List<Disk> result = query.list();
        return result;
    }

}
