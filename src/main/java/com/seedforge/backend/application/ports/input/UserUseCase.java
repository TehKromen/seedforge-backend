package com.seedforge.backend.application.ports.input;

import com.seedforge.backend.domain.model.PaginatedResult;

import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.model.criteria.UserSearchCriteria;

import java.util.Optional;

public interface UserUseCase {

    PaginatedResult<User> getAllUsers(UserSearchCriteria criteria);
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User update(Long id, User user);
    void delete(Long id);
    Optional <User> getUserByEmail(String email);
}
