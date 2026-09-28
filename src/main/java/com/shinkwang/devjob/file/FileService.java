package com.shinkwang.devjob.file;

import com.shinkwang.devjob.exception.BusinessException;
import com.shinkwang.devjob.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;

    public String upload(MultipartFile file) {
        validate(file);

        String ext = getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

        try {
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);
            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("파일 업로드 실패: {}", file.getOriginalFilename(), e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }
        return baseUrl + "/" + filename;
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.EMPTY_FILE);
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException(ErrorCode.FILE_TOO_LARGE);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null) return "";
        int dot = originalFilename.lastIndexOf('.');
        return (dot == -1) ? "" : originalFilename.substring(dot + 1);
    }
}
