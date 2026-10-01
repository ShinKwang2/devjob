package com.shinkwang.devjob.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record JobUpdateRequest(
        @NotBlank @Size(max = 200)
        @Pattern(
                regexp = "^[^\\r\\n]*$",
                message = "공고 제목에는 줄바꿈을 포함할 수 없습니다"
        )
        String title,

        String description,
        @Min(0) Integer salary,
        @FutureOrPresent LocalDate deadline
) {}
