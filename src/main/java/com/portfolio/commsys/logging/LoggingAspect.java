package com.portfolio.syscomm.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

@Slf4j      // Lombok의 Slf4j를 사용해 로거 자동 생성
@Aspect     // AOP Aspect임을 선언
//@Component  // Spring이 Aspect를 Bean을 등록 후 관리 autoConfiguration에 등록되어 삭제
public class LoggingAspect {

    // Advice: 적용 지점에서 실행 로직
    @Around("execution(* com.portfolio.syscomm..*Service.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint jointPoint) throws Throwable {

        // 메서드 정보 추출
        String methodName = jointPoint.getSignature().toShortString();
        Object[] args = jointPoint.getArgs();
        log.info("==> Method Start: {}, Args: {}", methodName, args);

        // 실행 시간 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result;
        try {
            // 대상 메서드 실행
            result = jointPoint.proceed();
            return result;
        } finally {
            // 실행 시간 측정 종료
            stopWatch.stop();
            log.info("<== Method End: {}, Execution Time: {}ms", methodName, stopWatch.getTotalTimeMillis());
//            log.info("[logMethodExcution] Method End : {}, Execution Time : {}ms", methodName, stopWatch.getTotalTimeMillis());
        }
    }
}
