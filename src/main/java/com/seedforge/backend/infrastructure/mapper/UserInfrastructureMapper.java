package com.seedforge.backend.infrastructure.mapper;

import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfrastructureMapper {
    UserEntity toEntity(User User);
    User toDomain(UserEntity userEntity);
}
