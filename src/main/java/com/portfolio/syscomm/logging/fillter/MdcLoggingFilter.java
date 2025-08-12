package com.portfolio.syscomm.logging.fillter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
//@Component  // autoConfiguration에 등록되어 삭제
@Order(1)   // 다른 필터보다 먼저 실행 (순서진행)
public class MdcLoggingFilter implements Filter {
    /** TraceID 생성을 위한 MDC 필터 구현
     * 모든 로그에 고유 ID를 부여하는
     * */

    private static final String TRACE_ID_HEADER = "X-Trace_ID";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 외부(API gateway등)에서 이미 Trace ID를 생성해 헤더에 넣었는지 확인
        String existingTraceId = httpRequest.getHeader(TRACE_ID_HEADER);

        // Trace ID가 없으면 생성, 있으면 기존 ID 사용
        String traceId = (existingTraceId != null) ? existingTraceId : UUID.randomUUID().toString().substring(0, 8);

        // MDC에 Trace ID를 저장. 이 시점부터 이 스레드의 모든 로그에 traceId가 포함됨.
        MDC.put(TRACE_ID_MDC_KEY, traceId);
        log.info("Request start with Trace ID: {}", traceId);

        try{
            // 다음 필터 또는 서블릿으로 요청을 전달
            chain.doFilter(request, response);
        }finally {
            // 요청 처리가 모두 끝난 후, 반드시 MDC에서 Trace ID를 제거해야 함
            // 제거하지 않으면 스레드 풀 재사용 시 다른 요청에 이 ID가 잘못 사용될 수 있음
            log.info("Request finished with Trace ID: {}", traceId);
            MDC.clear();
        }
    }
}
