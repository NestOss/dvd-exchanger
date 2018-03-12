package com.nestos.dvdexchanger.repository.disk;

import com.nestos.dvdexchanger.entity.Disk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Roman Osipov
 */
@Repository
public interface DiskRepository extends CrudRepository<Disk, Long>, DiscRepositoryCustom {
    Disk findOneByNameAndInstanceTag(String name, Integer instanceTag);
}
