package com.nestos.dvdexchanger.repository.user;

import com.nestos.dvdexchanger.entity.User;

public interface UserRepositoryCustom {

    /**
     * Загружает сущность пользователя со списком принадлежащих ему дисков
     * @param id идентификатор пользователя
     * @return загруженную сущность пользователя или null, если сущность не найдена
     */
    User fetchOne(Long id);

}
