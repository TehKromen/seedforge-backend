package com.seedforge.backend.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Table(name = "roles",
       uniqueConstraints = @UniqueConstraint(columnNames = {"code", "organization_id"}))
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column
    private String code;
    @Column
    private String description;
}
