package com.seedforge.backend.infrastructure.adapters.output.persistence.repository;


import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT u.* FROM users u WHERE " +
            "(:name IS NULL OR u.first_name LIKE CONCAT('%', :name, '%')) AND " +
            "(:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) AND " +
            "(:roleId IS NULL OR u.role_id = :roleId)", nativeQuery = true)
    Page<UserEntity> findAllByFilter(
            @Param("name") String name,
            @Param("email") String email,
            @Param("roleId") Long roleId,
            Pageable pageable);
}
