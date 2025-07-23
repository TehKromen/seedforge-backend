package com.seedforge.backend.infrastructure.adapters.input.mapper;

import com.seedforge.backend.domain.model.Role;
import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toDomain(RoleEntity entity);

    RoleEntity toEntity(Role domain);

    List<Role> toDomainAll(List<RoleEntity> entities);
}
