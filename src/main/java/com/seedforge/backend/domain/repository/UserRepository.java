package com.seedforge.backend.domain.repository;

import com.seedforge.backend.common.util.enums.Role;
import com.seedforge.backend.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    public Optional<User> findById(Long id);
    public User save(User user);
    public void delete(Long id);
    public User update(Long id, User user);
    public Page<User> findAll(Specification<User> spec, Pageable pageable);
}
