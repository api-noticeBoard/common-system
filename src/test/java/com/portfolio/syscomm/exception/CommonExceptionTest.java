package com.portfolio.syscomm.exception;

import com.portfolio.syscomm.code.ErrorCode;
import com.portfolio.syscomm.common.exception.CommonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommonExceptionTest {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionTest.class);

    @Test
    @DisplayName("CommonException 테스트")
    void createCommonExceptionTest() {

        // given
        final ErrorCode errCode = ErrorCode.INVALID_INPUT_VALUE;

        // when
        CommonException exception = new CommonException(errCode);
        log.error("exception : {}", exception);
        // then
        // 생성된 예외 객체 null check
        assertThat(exception).isNotNull();
        // 예외 객체가 생성 시 주입받는 ErrorCode를 가졌는지 check
        assertThat(exception.getErrorCode()).isEqualTo(errCode);
        // 예외 객체 메시지가 ErrorCode 메시지와 일지 check
        assertThat(exception.getMessage()).isEqualTo(errCode.getMessage());
    }

    @Test
    @DisplayName("의도한 ErrorCode를 포함해 예외발생 검증")
    void whenExceptionThrown(){

        // given
        final ErrorCode code = ErrorCode.METHOD_NOT_ALLOWED;

        // when
        CommonException thrownException = assertThrows(CommonException.class, () -> {
           throw new CommonException(code);
        });
        log.error("thrownException : {}", thrownException);
        // then
        assertThat(thrownException.getErrorCode()).isEqualTo(code);
    }
}
