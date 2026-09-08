package com.shinkwang.devjob.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobCreateRequest(
        @NotBlank String title,
        @NotNull Long companyId,
        @Min(0) Integer salary,
        String description
) {}
