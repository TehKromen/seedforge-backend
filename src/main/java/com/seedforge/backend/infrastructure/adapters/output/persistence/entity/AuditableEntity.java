package com.seedforge.backend.infrastructure.adapters.output.persistence.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

@MappedSuperclass
@Audited
@Data
public class AuditableEntity implements Serializable {
    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
    @Column
    private String createdBy;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column
    private String updatedBy;
}
