package com.portfolio.syscomm.handler;


import com.portfolio.syscomm.code.ErrorCode;
import com.portfolio.syscomm.common.exception.CommonException;
import com.portfolio.syscomm.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 모든 @RestController에서 발생하는 예외를 처리
public class GlobalExceptionHandler {

    /** 직접 정의한 CustomException 처리 */
    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CommonException ex){
        log.error("handleCustomException: {}", ex.getErrorCode());
        final ErrorResponse response = ErrorResponse.of(ex.getErrorCode());

        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    /** @Valid를 통한 유효성 검증 실패 시 발생하는 예외 처리 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex){
        log.error("handleMethodArgumentNotVaildException: {}", ex.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /** 지원하지 않는 HTTP 메서드 요청 시 발생하는 예외 처리 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex){
        log.error("httpRequestMethodNotSupportedException : {}", ex.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED );
    }

    /** 위에서 처리 못한 모든 예외 처리 */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception ex){
        log.error("handleException: {}", ex.getMessage(), ex);
        // 스택 트레이스 로깅
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
