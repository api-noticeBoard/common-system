package com.portfolio.syscomm.common.exception;

import com.portfolio.syscomm.code.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode){
        // RuntimeException의 메세지 필드에 errorCode의 메시지를 저장
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}