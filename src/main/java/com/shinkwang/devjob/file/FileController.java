package com.shinkwang.devjob.file;

import com.shinkwang.devjob.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController implements FileApiDocs {

    private final FileService fileService;

    @Override
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) {
        String url = fileService.upload(file);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(url));
    }
}
