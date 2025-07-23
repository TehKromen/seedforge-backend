package com.seedforge.backend.infrastructure.adapters.input.controller;

import com.seedforge.backend.application.ports.input.UserUseCase;
import com.seedforge.backend.domain.model.*;
import com.seedforge.backend.infrastructure.adapters.input.dto.UserRequestDTO;
import com.seedforge.backend.infrastructure.adapters.input.dto.UserResponseDTO;
import com.seedforge.backend.domain.model.criteria.UserSearchCriteria;
import com.seedforge.backend.infrastructure.adapters.input.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    public UserController(UserUseCase userUseCase, UserMapper userMapper) {
        this.userUseCase = userUseCase;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<PaginatedResult<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Boolean active) {

        UserSearchCriteria criteria = new UserSearchCriteria(page, size, sortBy, sortOrder, firstName, email, roleId, country, active);
        logger.info("Getting users with criteria: {}", criteria);
        return ResponseEntity.ok(userUseCase.getAllUsers(criteria));
    }

    @Operation(
            summary = "Update user",
            description = "Updates a user into the database with new information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Updated successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Parameter(description = "User") @Valid @RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setId(id);
        logger.info("Updating user");
        User user = userMapper.toDomain(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUseCase.update(id, user));
    }

    @Operation(
            summary = "Delete user",
            description = "Delete a user from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Deleted successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id) {
        logger.info("Deleting user with id: {}", id);
        userUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Create user",
            description = "Creates a new user into the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PostMapping()
    public ResponseEntity<User> createUser(
            @Parameter(description = "User")
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
            User user = userMapper.toDomain(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUseCase.createUser(user));
    }

    @Operation(
            summary = "Get user by id",
            description = "Retrieves a user by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User with the given id found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "User with the given id not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long id) {
        logger.info("Getting user by id");
        Optional<User> user = userUseCase.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserResponseDTO userResponseDTO = this.userMapper.toResponseDTO(user.get());
        return ResponseEntity.ok(userResponseDTO);

    }
}
