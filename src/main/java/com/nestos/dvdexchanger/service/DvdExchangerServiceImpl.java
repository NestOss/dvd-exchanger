package com.nestos.dvdexchanger.service;

import com.nestos.dvdexchanger.entity.Disk;
import com.nestos.dvdexchanger.entity.TakenItem;
import com.nestos.dvdexchanger.entity.User;
import com.nestos.dvdexchanger.exception.BusinessLogicConstraintViolationException;
import com.nestos.dvdexchanger.repository.disk.DiskRepository;
import com.nestos.dvdexchanger.repository.takenitem.TakenItemRepository;
import com.nestos.dvdexchanger.repository.user.UserRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DvdExchangerServiceImpl implements DvdEchangerService {

    private static Logger log = LoggerFactory.getLogger(DvdEchangerService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiskRepository diskRepository;

    @Autowired
    private TakenItemRepository takenItemRepository;

    /**
     * Заводит новый диск disk - собственность пользователя user
     *
     * @param disk   добавляемый диск
     * @param userId идентификатор пользователя
     */

    @Override
    public void addDisk(Disk disk, Long userId) {
        log.info("user[{}] action = addDisk, disk = {}", userId, disk);
        Validate.notNull(disk, "Disk should not be null");
        Validate.notNull(userId, "userId should not be null");
        Validate.notBlank(disk.getName(), "Disk name should be not null or empty");
        Validate.notNull(disk.getInstanceTag(), "Disk instanceTag should be not null");

        Disk managedDisk = diskRepository.findOneByNameAndInstanceTag(disk.getName(), disk.getInstanceTag());
        log.debug("user[{}] action = addDisk, disk = {}, managedDisk = {}", userId, disk, managedDisk);

        // failfast (при модификации в параллельных транзакциях сработает ограничение целостности на таблице)
        if (managedDisk != null) {
            throw new BusinessLogicConstraintViolationException("Disk " + disk + " already exists");
        }

        // подгрузка сущности в контекст персистентности вместе со списком дисков
        User managedUser = userRepository.fetchOne(userId);
        log.debug("user[{}] action = addDisk, disk = {}, managedUser = {}", userId, disk, managedUser);
        if (managedUser == null) {
            throw new BusinessLogicConstraintViolationException("Can't find user " + userId);
        }

        disk.setUser(managedUser);
        managedUser.getDisks().add(disk);

        userRepository.save(managedUser); // диск сохранится каскадом
        log.debug("user[{}] action = addDisk, disk = {}, savedDisk = {}", userId, disk, managedDisk);
        log.trace("user[{}] action = addDisk, disk = {}, userDisks = {}", userId, disk, managedUser.getDisks());

    }

    /**
     * Аутентификация пользователя
     *
     * @param userName имя пользователя
     * @return пользователь, идентифицируемый по id
     */
    @Override
    public User authentificateUser(String userName) {
        log.info("Authentificate userName = {}", userName);
        Validate.notBlank(userName, "User name should be not null or empty");

        User user = userRepository.findOneByName(userName);
        log.debug("Authenticate find user = {}", user);
        if (user != null) {
            log.debug("User name = {} already exists, return this user", userName);
            return user;
        }

        log.debug("User name = {} not exists, create new one", userName);
        User newUser = new User();
        newUser.setName(userName);
        userRepository.save(newUser);
        log.debug("Authenticate return user {}", newUser);
        return newUser;
    }

    /**
     * Пользователь берет диск
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     * @return сущность-связку User-Disk
     */
    @Override
    public TakenItem userTakeDisk(Long diskId, Long userId) { // проверить, не берет ли пользователь диск у себя
        log.info("user[{}] action = userTakeDisk, disk = {}", userId, diskId);
        Validate.notNull(diskId, "diskId should not be null");
        Validate.notNull(userId, "userId should not be null");

        Disk managedDisk = diskRepository.findOne(diskId);
        log.debug("user[{}] action = userTakeDisk, diskId = {}, managedDisk = {}", userId, diskId, managedDisk);
        if (managedDisk == null) {
            throw new BusinessLogicConstraintViolationException("Managed disk with id = " + diskId + " not exists");
        }

        User managedUser = userRepository.fetchOne(userId);
        log.debug("user[{}] action = userTakeDisk, diskId = {}, managedUser = {}", userId, diskId, managedUser);
        if (managedUser == null) {
            throw new BusinessLogicConstraintViolationException("User with id = " + userId + " not exists");
        }

        // пользователь не может взять диск у себя
        if (managedUser.getDisks().contains(managedDisk)) {
            throw new BusinessLogicConstraintViolationException("User is a owner of this disk");
        }

        TakenItem takenItem = new TakenItem();
        takenItem.setDisk(managedDisk);
        takenItem.setUser(managedUser);

        TakenItem savedTakenItem = takenItemRepository.save(takenItem);
        log.debug("user[{}] action = userTakeDisk, diskId = {}, savedTakenItem = {}", userId, diskId, savedTakenItem);

        return takenItem;
    }

    /**
     * Пользователь возвращает диск
     *
     * @param diskId идентификатор диска
     * @param userId идентификатор пользователя
     */
    @Override
    public void userReturnDisk(Long diskId, Long userId) {
        log.info("user[{}] action = userReturnDisk, disk = {}", userId, diskId);
        Validate.notNull(diskId, "diskId should not be null");
        Validate.notNull(userId, "userId should not be null");
        takenItemRepository.deleteByDiskIdAndUserId(diskId, userId);
        // понятно, что успех будет только после коммита транзакции
        log.info("user[{}] after action = userReturnDisk, disk = {}", userId, diskId);
    }

    /**
     * Возвращает список свободных дисков
     *
     * @return список свободных дисков
     */
    @Override
    public List<Disk> findFreeDisks() {
        log.info("Call findFreeDisks");
        List<Disk> freeDisks = diskRepository.findFreeDisks();
        log.debug("Return FindFreeDisks [{}]", freeDisks.size());
        log.trace("Return FindFreeDisks = {}", freeDisks);
        return freeDisks;
    }

    /**
     * Возвращает список дисков, взятых пользователем
     * @param userId идентификатор пользователя
     * @return список дисков, взятых пользователем
     */
    @Override
    public List<Disk> findDisksTakenByUser(Long userId) {
        log.info("user[{}] Call findDisksTakenByUser", userId);
        Validate.notNull(userId, "userId should not be null");
        List<Disk> disks = takenItemRepository.findDisksTakenByUser(userId);
        log.debug("user[{}] Return findDisksTakenByUser [{}]", userId, disks.size());
        log.trace("user[{}] Return findDisksTakenByUser disks = {}", userId, disks);
        return disks;
    }

    /**
     * Возвращает список дисков, взятых у пользователя
     * @param userId идентификатор пользователя
     * @return список дисков, взятых у пользователя
     */
    @Override
    public List<TakenItem> findTakenItemsFromUser(Long userId) {
        log.info("user[{}] Call findTakenItemsFromUser", userId);
        Validate.notNull(userId, "userId should not be null");
        List<TakenItem> takenItems = takenItemRepository.findTakenItemsFromUser(userId);
        log.debug("user[{}] Return findTakenItemsFromUser [{}]", userId, takenItems.size());
        log.trace("user[{}] Return findTakenItemsFromUser takenItems = {}", userId, takenItems);
        return takenItems;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setDiskRepository(DiskRepository diskRepository) {
        this.diskRepository = diskRepository;
    }

    public void setTakenItemRepository(TakenItemRepository takenItemRepository) {
        this.takenItemRepository = takenItemRepository;
    }
}
