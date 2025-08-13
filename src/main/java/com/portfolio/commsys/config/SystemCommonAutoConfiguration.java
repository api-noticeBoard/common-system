package com.portfolio.syscomm.config;

import com.portfolio.syscomm.handler.GlobalExceptionHandler;
import com.portfolio.syscomm.logging.LoggingAspect;
import com.portfolio.syscomm.logging.fillter.MdcLoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration  // 이 클래스가 Spring 설정 명시
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@EnableAspectJAutoProxy(proxyTargetClass = true) // AOP 기능을 명시적으로 활성화
@Import({GlobalExceptionHandler.class,
        LoggingAspect.class,
        MdcLoggingFilter.class  // MdcLoggingFilter를 빈으로 등록하도록 추가
})
public class SystemCommonAutoConfiguration {
    // 내용이 없어도 이 클래스의 목적은 관련 빈들을 그룹화 및 자동 구성의 진입점 역할
}
