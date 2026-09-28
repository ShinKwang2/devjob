package com.shinkwang.devjob.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record JobUpdateRequest(
        @NotBlank String title,
        String description,
        @Min(0) Integer salary,
        @FutureOrPresent LocalDate deadline
) {}
