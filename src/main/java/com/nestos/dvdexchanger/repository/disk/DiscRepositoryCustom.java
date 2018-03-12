package com.nestos.dvdexchanger.repository.disk;

import com.nestos.dvdexchanger.entity.Disk;

import java.util.List;

public interface DiscRepositoryCustom {

    /**
     * Возвращает список незанятых дисков
     *
     * @return список незанятых дисков
     */
    List<Disk> findFreeDisks();
}
