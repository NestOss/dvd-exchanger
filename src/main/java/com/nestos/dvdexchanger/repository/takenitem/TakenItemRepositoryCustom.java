package com.nestos.dvdexchanger.repository.takenitem;

import com.nestos.dvdexchanger.entity.Disk;
import com.nestos.dvdexchanger.entity.TakenItem;

import java.util.List;

public interface TakenItemRepositoryCustom {

    /**
     * Удаляет связь User-Disk
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     */
    void deleteByDiskIdAndUserId(Long diskId, Long userId);

    /**
     * Возвращает список дисков, взятых пользователем
     *
     * @param userId идентификатор пользователя
     * @return
     */
    List<Disk> findDisksTakenByUser(Long userId);

    /**
     * Возвращает список дисков, взятых у пользователя
     *
     * @param userId идентификатор пользователя
     * @return
     */
    List<TakenItem> findTakenItemsFromUser(Long userId);
}
