package com.nestos.dvdexchanger.repository.user;

import com.nestos.dvdexchanger.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ДАО пользователей
 * @author Roman Osipov
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

    /**
     * Поиск пользователя по имени
     * @param name имя пользователя
     * @return найденного пользователя с именем name, или null если пользователь с таким именем отсутствует
     */
    User findOneByName(String name);
    
}
