package com.seedforge.backend.domain.repository;

import java.util.List;
import java.util.Optional;

import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.Role;

public interface RoleRepository {

    List<Role> getAllRoles();
    Optional<Role> findById(Long id);
    PaginatedResult<Role> findByFilter(String code, Long organizationId, int page, int size, String sortBy, String sortOrder);
    Role save(Role role);
    void delete(Long id);

}
