package com.nestos.dvdexchanger.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DVD-диск
 */
@Entity
@Table(
        uniqueConstraints
                = @UniqueConstraint(columnNames = {"name", "instanceTag"})
)
public class Disk implements Serializable {

    public static final int MAX_NAME_LENGTH = 128;

    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Наименование
    @Column(length = MAX_NAME_LENGTH, nullable = false)
    private String name;

    // Номер экземпляра
    private Integer instanceTag;

    // Собственник диска
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public Integer getInstanceTag() {
        return instanceTag;
    }

    public void setInstanceTag(Integer instanceTag) {
        this.instanceTag = instanceTag;
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
                append(id).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false, null,
                "name", "instanceTag", "user");
    }

}
