package com.nestos.dvdexchanger.repository.takenitem;

import com.nestos.dvdexchanger.entity.Disk;
import com.nestos.dvdexchanger.entity.TakenItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

public class TakenItemRepositoryImpl implements TakenItemRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * Удаляет связь User-Disk
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     */
    // можно реализовать проще с помощью SpringData deleteBy...
    // здесь показывается применение Criteria API
    public void deleteByDiskIdAndUserId(Long diskId, Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<TakenItem> delete = cb.createCriteriaDelete(TakenItem.class);
        Root root = delete.from(TakenItem.class);
        delete.where(
                cb.and(
                        cb.equal(root.get("disk").get("id"), diskId),
                        cb.equal(root.get("user").get("id"), userId)
                ));

        em.createQuery(delete).executeUpdate();
    }

    /**
     * Возвращает список дисков, взятых пользователем
     *
     * @param userId идентификатор пользователя
     * @return
     */
    @Override
    public List<Disk> findDisksTakenByUser(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Disk> query = cb.createQuery(Disk.class);
        Root<TakenItem> takenItem = query.from(TakenItem.class);
        query.select(takenItem.get("disk"));
        query.where(cb.equal(takenItem.get("user").get("id"), userId));
        List<Disk> disks = em.createQuery(query).getResultList();
        return disks;
    }

    /**
     * Возвращает список дисков, взятых у пользователя
     * @param userId идентификатор пользователя
     * @return
     */
    @Override
    public List<TakenItem> findTakenItemsFromUser(Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TakenItem> query = cb.createQuery(TakenItem.class);
        Root<TakenItem> takenItem = query.from(TakenItem.class);
        Join<TakenItem, Disk> joinDisk = takenItem.join("disk", JoinType.INNER);
        query.select(takenItem).where(cb.equal(joinDisk.get("user").get("id"), userId));
        List<TakenItem> takenItems = em.createQuery(query).getResultList();
        return takenItems;
    }

}
