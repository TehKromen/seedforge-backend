package com.seedforge.backend.application.ports.input;

import com.seedforge.backend.application.dto.UserRequestDTO;
import com.seedforge.backend.application.dto.UserResponseDTO;
import com.seedforge.backend.common.util.enums.Role;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserUseCase {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> getUserById(Long id);
    void deleteUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    Page<UserResponseDTO> getAllUsers(int page, int size, String sortBy, String sortOrder, String name, String email, Role role);
}
