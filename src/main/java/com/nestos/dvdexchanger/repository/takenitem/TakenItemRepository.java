package com.nestos.dvdexchanger.repository.takenitem;

import com.nestos.dvdexchanger.entity.TakenItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Roman Osipov
 */
@Repository
public interface TakenItemRepository extends CrudRepository<TakenItem, Long>, TakenItemRepositoryCustom {
    
}
