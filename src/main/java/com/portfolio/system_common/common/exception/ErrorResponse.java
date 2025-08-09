package com.portfolio.system_common.common.exception;

import com.portfolio.system_common.code.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;         // HTTP 상태코드
    private int code;           // 커스텀 에러 코드
    private String message;     // 에러 메시지

    private ErrorResponse(final ErrorCode code) {
        this.status = code.getHttpStatus().value();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    // ErrorCode를 받아서 ErrorResponse 객체를 생성하는 정적 팩토리 메서드
    public static ErrorResponse of(final ErrorCode code){
        return new ErrorResponse(code);
    }
}
