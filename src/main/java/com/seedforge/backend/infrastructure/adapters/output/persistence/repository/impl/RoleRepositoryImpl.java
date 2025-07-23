package com.seedforge.backend.infrastructure.adapters.output.persistence.repository.impl;

import com.seedforge.backend.common.util.enums.SortDir;
import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.Role;
import com.seedforge.backend.domain.repository.RoleRepository;
import com.seedforge.backend.infrastructure.adapters.input.mapper.RoleMapper;
import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.RoleEntity;
import com.seedforge.backend.infrastructure.adapters.output.persistence.repository.JpaRoleRepository;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;
    private final MessageSource messageSource;

    public RoleRepositoryImpl(JpaRoleRepository jpaRoleRepository, RoleMapper roleMapper, MessageSource messageSource) {
        this.jpaRoleRepository = jpaRoleRepository;
        this.roleMapper = roleMapper;
        this.messageSource = messageSource;
    }

    public List<Role> getAllRoles(){
        List<RoleEntity> roles = this.jpaRoleRepository.findAll();
        return this.roleMapper.toDomainAll(roles);
    }

    public Optional<Role> findById(Long id) {
        return this.jpaRoleRepository.findById(id).map(this.roleMapper::toDomain);
    }

    @Override
    public PaginatedResult<Role> findByFilter(String code, Long organizationId, int page, int size, String sortBy, String sortDirection) {
        SortDir sortDir = SortDir.valueOf(sortDirection.toUpperCase());
        Sort.Direction direction = Sort.Direction.valueOf(sortDir.name());
        final Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (code == null || code.trim().isEmpty()) {
            code = null;
        } else {
            code = "%" + code.trim() + "%";
        }

        Page<RoleEntity> roles = this.jpaRoleRepository.findByFilter(code, organizationId, pageable);
        return new PaginatedResult<>(
                page,
                size,
                roles.getTotalElements(),
                roles.getTotalPages(),
                roles.getContent().stream().map(this.roleMapper::toDomain).toList()
        );
    }


    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = this.roleMapper.toEntity(role);
        RoleEntity savedRole = this.jpaRoleRepository.save(roleEntity);
        return this.roleMapper.toDomain(savedRole);
    }

    @Override
    public void delete(Long id) {
        this.jpaRoleRepository.deleteById(id);
    }
}
