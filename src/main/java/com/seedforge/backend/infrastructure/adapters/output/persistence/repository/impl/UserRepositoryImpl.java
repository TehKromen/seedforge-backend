package com.seedforge.backend.infrastructure.adapters.output.persistence.repository.impl;

import com.seedforge.backend.common.util.enums.SortDir;
import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.model.criteria.UserSearchCriteria;
import com.seedforge.backend.domain.repository.UserRepository;
import com.seedforge.backend.infrastructure.adapters.input.mapper.UserMapper;
import com.seedforge.backend.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.seedforge.backend.infrastructure.adapters.output.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {


    private final JpaUserRepository jpaUserRepository;
    private final MessageSource messageSource;
    private final UserMapper userMapper;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, MessageSource messageSource, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.messageSource = messageSource;
        this.userMapper = userMapper;
    }

    public Optional<User> findById(Long id){
        UserEntity user = this.jpaUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("user.error.not_found", new Object[]{id}, LocaleContextHolder.getLocale())
                ));



        return Optional.ofNullable(this.userMapper.toDomain(user));
    }

    public User save(User user){
        UserEntity entity = this.jpaUserRepository.save(this.userMapper.toEntity(user));
        return this.userMapper.toDomain(entity);
    }

    public void delete(Long id){
        this.jpaUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("user.error.not_found", new Object[]{id}, LocaleContextHolder.getLocale())));
        this.jpaUserRepository.deleteById(id);
    }


    public User update(Long id, User user){
        this.jpaUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        messageSource.getMessage("user.error.not_found", new Object[]{id}, LocaleContextHolder.getLocale())));
        user.setId(id);
        UserEntity updatedEntity = this.jpaUserRepository.save(this.userMapper.toEntity(user));
        return this.userMapper.toDomain(updatedEntity);
    }

    @Override
    public PaginatedResult<User> findAll(UserSearchCriteria criteria) {
        SortDir sortDir;
        try {
            sortDir = SortDir.valueOf(criteria.getSortOrder().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageSource.getMessage(
                    "generic.error.invalid_sort_order", new Object[]{criteria.getSortOrder()}, LocaleContextHolder.getLocale()));
        }

        Sort.Direction direction = Sort.Direction.valueOf(sortDir.name());
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, criteria.getSortBy()));

        String nameFilter = (criteria.getFirstName() == null || criteria.getFirstName().isEmpty()) ? "%" : "%" + criteria.getFirstName() + "%";
        String emailFilter = (criteria.getEmail() == null || criteria.getEmail().isEmpty()) ? "%" : "%" + criteria.getEmail() + "%";
        Long role = criteria.getRoleId();

        Page<UserEntity> pageResult = jpaUserRepository.findAllByFilter(
                nameFilter,
                emailFilter,
                role,
                pageable
        );

        List<User> users = pageResult.getContent().stream().map(userMapper::toDomain).collect(Collectors.toList());

        return new PaginatedResult<>(
                criteria.getPage(),
                criteria.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                users
        );
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }
}
