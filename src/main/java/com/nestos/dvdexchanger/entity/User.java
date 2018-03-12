package com.nestos.dvdexchanger.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

import org.apache.commons.lang3.builder.*;

/**
 * Пользователь
 */
@Entity
@Table(name = "dvd_user") // во многих RDBMS - user зарезервированное слово
public class User implements Serializable {

    public static final int MAX_NAME_LENGTH = 128;

    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Имя
    @Column(length = MAX_NAME_LENGTH, nullable = false, unique = true)
    private String name;

    // Диски - собственность пользователя
    @ToStringExclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Disk> disks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Disk> getDisks() {
        return disks;
    }

    public void setDisks(List<Disk> disks) {
        this.disks = disks;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(id).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false, null,
                "name", "disks");
    }
}
