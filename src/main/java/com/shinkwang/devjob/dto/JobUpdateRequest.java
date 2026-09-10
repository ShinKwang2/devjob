package com.shinkwang.devjob.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record JobUpdateRequest(
        @NotBlank String title,
        String description,
        @Min(0) Integer salary
) {}
