package com.nestos.dvdexchanger.service;

import com.nestos.dvdexchanger.entity.Disk;
import com.nestos.dvdexchanger.entity.TakenItem;
import com.nestos.dvdexchanger.entity.User;

import java.util.List;

public interface DvdEchangerService {

    /**
     * Заводит новый диск disk - собственность пользователя user
     *
     * @param disk   добавляемый диск
     * @param userId идентификатор пользователя
     */
    void addDisk(Disk disk, Long userId);

    /**
     * Аутентификация пользователя
     *
     * @param userName имя пользователя
     * @return пользователь, идентифицируемый по id
     */
    User authentificateUser(String userName);

    /**
     * Пользователь берет диск
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     * @return
     */
    TakenItem userTakeDisk(Long diskId, Long userId);

    /**
     * Пользователь возвращает диск
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     */
    void userReturnDisk(Long diskId, Long userId);

    /**
     * Возвращает список свободных дисков
     *
     * @return список свободных дисков
     */
    List<Disk> findFreeDisks();

    /**
     * Возвращает список дисков, взятых пользователем
     *
     * @param userId идентификатор пользователя
     * @return список дисков, взятых пользователем
     */
    List<Disk> findDisksTakenByUser(Long userId);

    /**
     * Возвращает список дисков, взятых у пользователя
     *
     * @param userId идентификатор пользователя
     * @return список дисков, взятых у пользователя
     */
    List<TakenItem> findTakenItemsFromUser(Long userId);

}
