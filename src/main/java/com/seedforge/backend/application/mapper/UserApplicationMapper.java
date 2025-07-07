package com.seedforge.backend.application.mapper;

import com.seedforge.backend.application.dto.UserRequestDTO;
import com.seedforge.backend.application.dto.UserResponseDTO;
import com.seedforge.backend.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserApplicationMapper {

    UserResponseDTO toResponseDTO(User user);
    User toDomain(UserRequestDTO userRequestDTO);
}
