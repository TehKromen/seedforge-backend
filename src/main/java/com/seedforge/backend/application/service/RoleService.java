package com.seedforge.backend.application.service;

import com.seedforge.backend.application.ports.input.RoleUseCase;
import com.seedforge.backend.common.exception.NotFoundException;
import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.Role;
import com.seedforge.backend.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements RoleUseCase {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.getAllRoles();
    }

    @Override
    public PaginatedResult<Role> getAllRoles(String code, Long organizationId, int page, int pageSize, String sortBy, String sortDirection) {
        return this.roleRepository.findByFilter(code, organizationId, page, pageSize, sortBy, sortDirection);
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
        role.setId(id);
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        if (roleRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Role not found");
        }
        roleRepository.delete(id);
    }
}
