package com.seedforge.backend.infrastructure.adapters.output.persistence.repository;

import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("""
    SELECT r FROM RoleEntity r
    WHERE (:code IS NULL OR r.code LIKE :code)""")
    Page<RoleEntity> findByFilter(@Param("code") String code, @Param("organizationId") Long organizationId, Pageable pageable);

}
