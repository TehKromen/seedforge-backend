package com.seedforge.backend.application.service;

import com.seedforge.backend.application.dto.UserRequestDTO;
import com.seedforge.backend.application.dto.UserResponseDTO;
import com.seedforge.backend.application.mapper.UserApplicationMapper;
import com.seedforge.backend.application.ports.input.UserUseCase;
import com.seedforge.backend.common.exception.NotFoundException;
import com.seedforge.backend.common.util.enums.Role;
import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserUseCase {

    private final UserRepository userRepository;
    private final UserApplicationMapper userApplicationMapper;

    public UserService(UserRepository userRepository, UserApplicationMapper userApplicationMapper) {
        this.userRepository = userRepository;
        this.userApplicationMapper = userApplicationMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = this.userRepository.save(this.userApplicationMapper.toDomain(userRequestDTO));
        return this.userApplicationMapper.toResponseDTO(user);
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long id) {
        return Optional.ofNullable(this.userApplicationMapper.toResponseDTO(
                this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id))
        ));
    }

    @Override
    public void deleteUserById(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(userRequestDTO.getRole());

        User updatedUser = this.userRepository.save(user);

        return this.userApplicationMapper.toResponseDTO(updatedUser);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(int page, int size, String sortBy, String sortOrder, String name, String email, Role role) {
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (role != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), role));
        }

        Page<User> usersPage = userRepository.findAll(spec, pageable);

        return usersPage.map(this.userApplicationMapper::toResponseDTO);
    }
}
