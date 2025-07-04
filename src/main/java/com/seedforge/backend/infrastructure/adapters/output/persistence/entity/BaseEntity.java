package com.seedforge.backend.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {


    @Column
    private Date createdAt;
    @Column
    private String createdBy;
    @Column
    private Date updatedAt;
    @Column
    private String updatedBy;

//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = new Date();
//        this.updatedAt = new Date();
//        final User user = UserContext.getUserDetails();
//        this.createdBy = user.getName();
//        this.updatedBt = user.getName();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = new Date();
//        final User user = UserContext.getUserDetails();
//        this.createdBy = user.getName();
//    }

}
