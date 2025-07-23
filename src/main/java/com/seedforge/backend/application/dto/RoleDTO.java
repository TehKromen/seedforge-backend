package com.mytic.acepoint.application.dto;

import com.mytic.acepoint.domain.model.Auditable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends Auditable {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
}
