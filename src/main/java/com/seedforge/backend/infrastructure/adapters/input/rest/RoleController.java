package  com.mytic.acepoint.infrastructure.adapters.input.rest;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytic.acepoint.application.dto.RoleDTO;
import com.mytic.acepoint.application.ports.input.RoleUseCase;
import com.mytic.acepoint.domain.model.PaginatedResult;
import com.mytic.acepoint.domain.model.Role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
        summary = "Create a role",
        description = "Creates a new role into the database"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully",
                content = @Content(schema = @Schema(implementation = Role.class))),
        @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                content = @Content(schema = @Schema(implementation = Role.class))),
        @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PostMapping()
    public ResponseEntity<RoleDTO> createRole(
        @Parameter(description = "Role details to create a new role",
                content = @Content(mediaType = "application/json"))
        @Valid @RequestBody RoleDTO roleDTO
    ) {
        logger.info("Creating role: {}", roleDTO);
        if(roleDTO.getName().trim().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        RoleDTO newRole = roleUseCase.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }

    @Operation(
            summary = "Get a role",
            description = "Gets the role details found by the given id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role with the given id found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Role with the given id not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Optional<RoleDTO> roleDTO = roleUseCase.getRoleById(id);
        return roleDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get all roles",
            description = "Retrieves a list of all roles. Optional filters can be applied."
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
    public ResponseEntity<PaginatedResult<RoleDTO>> getAllRoles(
    @Parameter(description = "Page number (0-based)", example = "0")
    @RequestParam(defaultValue = "0") int page,
    @Parameter(description = "Number of records per page", example = "10")
    @RequestParam(defaultValue = "10") int size,
    @Parameter(description = "Sorting criteria (e.g., name, email)", example = "name")
    @RequestParam(defaultValue = "id") String sortBy,
    @Parameter(description = "Sort direction (ASC/DESC)", example = "ASC")
    @RequestParam(defaultValue = "DESC") String sortDirection,
    @Parameter(description = "Filter by role name", example = "ROLE_ADMIN")
    @RequestParam(defaultValue = "") String name) {
        logger.info("Getting all roles");
        PaginatedResult<RoleDTO> roles = this.roleUseCase.getAllRoles(page, size, sortBy, sortDirection, name);
        return ResponseEntity.ok(roles);
    }


    @Operation(
            summary = "Delete role",
            description = "Delete a role found by the given id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role with the given id not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "Role id", example = "1")
            @PathVariable Long id) {
                logger.info("Deleting role with id: {}", id);
        try {
            this.roleUseCase.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


    @Operation(
            summary = "Update role",
            description = "Update a role found by the given id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Validation error of required fields",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role with the given id not found",
                    content = @Content(schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "403", description = "User not authorized to perform this action",
                    content = @Content(schema = @Schema(implementation = Role.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(
            @Parameter(description = "Role id", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Role details to update",
                    content = @Content(mediaType = "application/json"))
            @Valid @RequestBody RoleDTO roleDTO) {
                logger.info("Updating role with id: {}", id);
                if(roleDTO.getName().trim().isEmpty()){
                    return ResponseEntity.badRequest().build();
                }

                return this.roleUseCase.getRoleById(id).map(roleFound -> {
                    RoleDTO roleUpdated = this.roleUseCase.updateRole(id, roleDTO);
                    return ResponseEntity.ok(roleUpdated);
                }).orElseGet(() -> ResponseEntity.notFound().build());
        }
}
