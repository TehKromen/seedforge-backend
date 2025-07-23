package com.mytic.acepoint.domain.repository;

import java.util.Optional;

import com.mytic.acepoint.domain.model.PaginatedResult;
import com.mytic.acepoint.domain.model.Role;

public interface RoleRepository {

    public Optional<Role> findById(Long id);
    public Role save(Role role);
    public void delete(Long id);
    public Role update(Long id, Role role);
    public PaginatedResult<Role> findAll(int page, int size, String sortBy, String sortOrder, String name);
}
