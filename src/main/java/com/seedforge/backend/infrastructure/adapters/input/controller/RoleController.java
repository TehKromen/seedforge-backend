package com.seedforge.backend.infrastructure.adapters.input.controller;

import com.seedforge.backend.application.ports.input.RoleUseCase;
import com.seedforge.backend.common.exception.NotFoundException;
import com.seedforge.backend.domain.model.PaginatedResult;
import com.seedforge.backend.domain.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@Tag(name = "Roles", description = "Endpoints for managing roles")
@Validated
@RestController()
@RequestMapping("/api/roles")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleUseCase roleUseCase;

    public RoleController(RoleUseCase roleUseCase) {
        this.roleUseCase = roleUseCase;
    }

    @Operation(
            summary = "Get all roles",
            description = "Retrieves a list of all roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of roles retrieved",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @GetMapping()
    public ResponseEntity<List<Role>> getRoles(){
        logger.info("Getting all roles");
        List<Role> roles = this.roleUseCase.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(
            summary = "Get all roles paginated",
            description = "Retrieves a list of all roles paginated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of roles retrieved",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @GetMapping("/filter")
    public ResponseEntity<PaginatedResult<Role>> getRolesFiltered(
      @RequestParam(name = "code", required = false) String code,
      @RequestParam(name = "organizationId", required = false) Long organizationId,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
      @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
      @RequestParam(name = "sortDirection", required = false, defaultValue = "DESC") String sortDirection
    ){
        logger.info("Getting all roles with pagination");
        PaginatedResult<Role> roles = this.roleUseCase.getAllRoles(code, organizationId, page, pageSize, sortBy, sortDirection);
        return ResponseEntity.ok(roles);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role retrieved",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        logger.info("Getting role with id {}", id);
        return ResponseEntity.ok(this.roleUseCase.getRoleById(id).orElseThrow(() -> new NotFoundException("Role not found")));
    }

    @Operation(
            summary = "Create a new role",
            description = "Creates a new role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PostMapping()
    public ResponseEntity<Role> create(@RequestBody Role role) {
        logger.info("Creating role {}", role);
        return ResponseEntity.created(null).body(this.roleUseCase.create(role));
    }

    @Operation(
            summary = "Update a role",
            description = "Updates an existing role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id ,@RequestBody Role role) {
        logger.info("Updating role {}", role);
        return ResponseEntity.ok(this.roleUseCase.update(id, role));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Role> delete(@PathVariable Long id) {
        logger.info("Deleting role with id {}", id);
        this.roleUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
