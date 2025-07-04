package com.seedforge.backend.application.dto;

import com.seedforge.backend.common.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;

}