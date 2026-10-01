package com.shinkwang.devjob.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 회원
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다"),

    // 인증
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다"),
    LOGIN_RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS, "로그인 시도가 너무 많습니다. 잠시 후 다시 시도해 주세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요하거나 인증 정보가 유효하지 않습니다."),

    // 공고
    JOB_NOT_FOUND(HttpStatus.NOT_FOUND, "채용 공고를 찾을 수 없습니다"),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "회사를 찾을 수 없습니다"),
    JOB_CLOSED(HttpStatus.BAD_REQUEST, "마감된 공고입니다"),

    // 지원
    ALREADY_APPLIED(HttpStatus.CONFLICT, "이미 지원한 공고입니다"),
    JOB_APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원 내역을 찾을 수 없습니다"),

    // 파일
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "빈 파일은 업로드할 수 없습니다"),
    FILE_TOO_LARGE(HttpStatus.BAD_REQUEST, "파일 크기는 5MB를 초과할 수 없습니다"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다"),

    // 공통
    FORBIDDEN(HttpStatus.FORBIDDEN, "이 작업을 수행할 권한이 없습니다"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
    INVALID_SORT_PROPERTY(HttpStatus.BAD_REQUEST, "정렬에 사용할 수 없는 필드입니다"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다"),
    DATA_CONFLICT(HttpStatus.CONFLICT, "이미 처리되었거나 현재 상태와 충돌합니다"),
    RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.")
    ;


    private final HttpStatus status;
    private final String message;
}
