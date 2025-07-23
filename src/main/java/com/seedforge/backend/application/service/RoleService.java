package com.mytic.acepoint.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mytic.acepoint.application.dto.RoleDTO;
import com.mytic.acepoint.application.mapper.RoleApplicationMapper;
import com.mytic.acepoint.application.ports.input.RoleUseCase;
import com.mytic.acepoint.domain.model.PaginatedResult;
import com.mytic.acepoint.domain.model.Role;
import com.mytic.acepoint.domain.repository.RoleRepository;

@Service
public class RoleService implements RoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleApplicationMapper roleApplicationMapper;

    public RoleService(RoleRepository roleRepository, RoleApplicationMapper roleApplicationMapper) {
        this.roleRepository = roleRepository;
        this.roleApplicationMapper = roleApplicationMapper;
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleApplicationMapper.toDomain(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleApplicationMapper.toDTO(savedRole);
    }


    @Override
    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(roleApplicationMapper::toDTO);
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleApplicationMapper.toDomain(roleDTO);
        roleRepository.update(id, role);
        return roleApplicationMapper.toDTO(role);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.delete(id);
    }

    @Override
    public PaginatedResult<RoleDTO> getAllRoles(int page, int size, String sortBy, String sortOrder, String name) {
        PaginatedResult<Role> usersPage = roleRepository.findAll(page, size, sortBy, sortOrder, name);
        return usersPage.map(roleApplicationMapper::toDTO);
    }

}
