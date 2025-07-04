package com.seedforge.backend.domain.model;

import com.seedforge.backend.common.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User  extends Base{
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
