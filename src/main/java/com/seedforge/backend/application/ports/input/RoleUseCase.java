package com.mytic.acepoint.application.ports.input;

import java.util.Optional;

import com.mytic.acepoint.application.dto.RoleDTO;
import com.mytic.acepoint.domain.model.PaginatedResult;

public interface RoleUseCase {
    RoleDTO createRole(RoleDTO roleDTO);
    Optional<RoleDTO> getRoleById(Long id);
    RoleDTO updateRole(Long id, RoleDTO role);
    void deleteRole(Long id);
    PaginatedResult<RoleDTO> getAllRoles(int page, int size, String sortBy, String sortOrder, String name);
}
