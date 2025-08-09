package com.portfolio.syscomm.common.aop.logging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j      // Lombok의 Slf4j를 사용해 로거 자동 생성
@Aspect     // AOP Aspect임을 선언
@Component  // Spring이 Aspect를 Bean을 등록 후 관리
public class LoggingAspect {

    // @Pointcut: 적용지점
    // @loggalbeMethods가 붙은 모든 메서드 대상
    // @Pointcut("@annotation()com.portolio.system_common.common.aop.loggingcom.portolio.system_common.common.aop.logging..annotation.Loggable)")
    public void loggalbeMethods() {
    }

    // Advice: 적용 지점에서 실행 로직
    @Around("loggableMethods()")
    public Object logMethodExecution(ProceedingJoinPoint jointPoint) throws Throwable {

        // 메서드 정보 추출
        String methodName = jointPoint.getSignature().toShortString();
        Object[] args = jointPoint.getArgs();
        log.debug("[logMethodExecution] Mathod Start : {}, Args : {}", methodName, args);

        // 실행 시간 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result;
        try {
            // 대상 메서드 실행
            result = jointPoint.proceed();
        } catch (Throwable throwable) {
            // 예외 로깅
            log.error("[logMethodExecution] Method Exception : {}, Message : {}", methodName, throwable.getMessage());

            // 예외를 다시 던져 원래 흐름을 방해하지 않음
            throw throwable;
        } finally {
            // 실행 시간 측정 종료
            stopWatch.stop();
            log.debug("[logMethodExceution] Method End : {}, Execution Time : {}ms", methodName, stopWatch.getTotalTimeMillis());
        }

        return result;
    }

}
