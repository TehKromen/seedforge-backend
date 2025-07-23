package com.seedforge.backend.domain.repository;

import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.model.criteria.UserSearchCriteria;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);
    User update(Long id, User user);
    PaginatedResult<User> findAll(UserSearchCriteria criteria);
    Optional<User> findByEmail(String email);

}
