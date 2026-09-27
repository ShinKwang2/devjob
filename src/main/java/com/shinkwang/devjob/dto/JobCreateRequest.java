package com.shinkwang.devjob.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JobCreateRequest(
        @NotBlank String title,
        @NotNull Long companyId,
        @Min(0) Integer salary,
        String description,
        @FutureOrPresent LocalDate deadline
) {}
