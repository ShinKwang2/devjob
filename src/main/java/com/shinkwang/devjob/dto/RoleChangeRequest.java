package com.shinkwang.devjob.dto;

import com.shinkwang.devjob.domain.Role;
import jakarta.validation.constraints.NotNull;

public record RoleChangeRequest(
        @NotNull Role role
) {}
