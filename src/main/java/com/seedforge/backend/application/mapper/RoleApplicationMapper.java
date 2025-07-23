package com.mytic.acepoint.application.mapper;

import org.mapstruct.Mapper;

import com.mytic.acepoint.application.dto.RoleDTO;
import com.mytic.acepoint.domain.model.Role;

@Mapper(componentModel = "spring")
public interface RoleApplicationMapper {

    RoleDTO toDTO(Role role);
    Role toDomain(RoleDTO role);
}
