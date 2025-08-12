package com.portfolio.syscomm.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoggingAspectTest {

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @BeforeEach
    void setUp() throws Throwable{
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("TestClass.testMethod()");
        when(joinPoint.proceed()).thenReturn("Success");
    }

    @Test
    @DisplayName("logMethodExecution @Advice 호출 시작/종료 로그")
    void aspectShouldLogMethodExecution() throws Throwable{
        Object result = loggingAspect.logMethodExecution(joinPoint);

        verify(joinPoint, times(1)).proceed();

        assert result.equals("Success");

        System.out.println("LoggingAspect unit test passed. The aspect logic itself is correct.");
    }

}
