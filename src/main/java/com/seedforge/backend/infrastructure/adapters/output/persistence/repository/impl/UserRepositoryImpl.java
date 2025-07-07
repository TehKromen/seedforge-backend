package com.seedforge.backend.infrastructure.adapters.output.persistence.repository.impl;

import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.repository.UserRepository;
import com.seedforge.backend.infrastructure.mapper.UserInfrastructureMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JpaRepository jpaRepository;
    private final UserInfrastructureMapper userInfrastructureMapper;

    public UserRepositoryImpl(JpaRepository jpaRepository, UserInfrastructureMapper userInfrastructureMapper) {
        this.jpaRepository = jpaRepository;
        this.userInfrastructureMapper = userInfrastructureMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return null;
    }
}
