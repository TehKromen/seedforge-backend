package com.seedforge.backend.application.service;

import com.seedforge.backend.application.ports.input.UserUseCase;
import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.User;
import com.seedforge.backend.domain.model.criteria.UserSearchCriteria;
import com.seedforge.backend.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserUseCase {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }


    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return this.userRepository.update(id, user);
    }

    public void delete(Long id){
        this.userRepository.delete(id);
    }

    @Override
    public PaginatedResult<User> getAllUsers(UserSearchCriteria criteria) {
        return userRepository.findAll(criteria);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
