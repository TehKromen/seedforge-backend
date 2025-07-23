package com.seedforge.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Auditable {
    private Long id;
    private String username;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
}
