package com.portfolio.syscomm.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /** --- 공통 에러 (1000번대) --- */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 1001, "입력값이 유효하지 않습니다.")
    , METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 1002, "지원하지 않는 HTTP 메서드 입니다.")
    , INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1003, "서버 오류가 발생했습니다.")
    , HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, 1004, "접근 권한이 없습니다.")
    /** 인증  관련 에러 (2000번대) --- */
    , AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, 2001, "인증에 실패하였습니다")
    /** 업무 도메인별 에러 (3000번대 : User, 4000번대 : Board 등) --- */
    , USER_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "사용자를 찾을 수 없습니다.")
    , EMAIL_DUPLICATION(HttpStatus.CONFLICT, 3002, "이미 사용중인 이메일 입니다.")
    , BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "게시글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}