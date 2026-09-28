package com.shinkwang.devjob.file;

import com.shinkwang.devjob.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일")
public interface FileApiDocs {

    @Operation(summary = "파일 업로드", security = @SecurityRequirement(name = "bearerAuth"))

    ResponseEntity<ApiResponse<String>> upload(MultipartFile file);
}
