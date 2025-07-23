package com.seedforge.backend.infrastructure.adapters.input.mapper;

import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.infrastructure.adapters.input.dto.UserRequestDTO;
import com.seedforge.backend.infrastructure.adapters.input.dto.UserResponseDTO;
import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User user);
    UserResponseDTO toResponseDTO(User user);
    User toDomain(UserRequestDTO userRequestDTO);
}
