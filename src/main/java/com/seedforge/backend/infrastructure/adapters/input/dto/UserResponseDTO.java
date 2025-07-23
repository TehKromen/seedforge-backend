package com.seedforge.backend.infrastructure.adapters.input.dto;

import com.seedforge.backend.domain.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.seedforge.backend.domain.model.Auditable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserResponseDTO extends Auditable {
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    private Role role;
    @NotBlank(message = "Email is required")
    private String email;
}
