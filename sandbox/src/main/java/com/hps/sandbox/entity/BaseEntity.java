package com.hps.sandbox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;


@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Version
    protected Long version;
    @CreationTimestamp
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Timestamp creationDate;
    @UpdateTimestamp
    @Column(name = "lastModifiedDate")
    private Timestamp lastModifiedDate;
    private boolean enabled;
    private boolean visible;
}
