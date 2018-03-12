package com.nestos.dvdexchanger.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Связка User-Disk
 */
@Entity
public class TakenItem implements Serializable {

    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Диск
     */
    @OneToOne
    @JoinColumn(name = "disk_id", nullable = false, unique = true)
    private Disk disk;

    /**
     * Пользователь, взявший диск
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(disk).
                append(user).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
