package com.seedforge.backend.domain.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchCriteria {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortOrder = "ASC";
    private String firstName;
    private String email;
    private Long roleId;
    private String country;
    private Boolean active;
}
