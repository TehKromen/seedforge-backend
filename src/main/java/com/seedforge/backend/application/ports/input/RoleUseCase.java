package com.seedforge.backend.application.ports.input;

import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleUseCase {

    List<Role> getAllRoles();

    PaginatedResult<Role> getAllRoles(String code, Long organizationId, int page, int pageSize, String sortBy, String sortDirection);

    Optional<Role> getRoleById(Long id);

    Role create(Role role);

    Role update(Long id, Role role);

    void delete(Long id);

}
