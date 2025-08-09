package com.portfolio.syscomm.config;

import com.portfolio.syscomm.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration  // 이 클래스가 Spring 설정 명시
@Import(GlobalExceptionHandler.class)
public class CommonAutoConfiguration {
    // 내용이 없어도 이 클래스의 목적은 관련 빈들을 그룹화 및 자동 구성의 진입점 역할
}
