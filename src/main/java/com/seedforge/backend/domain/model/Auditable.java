package com.seedforge.backend.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Auditable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
