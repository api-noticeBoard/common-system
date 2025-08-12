package com.portfolio.syscomm.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.portfolio.syscomm.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
@SpringBootTest(classes = TestApplication.class)    // 테스트용 application 지정하는 context 로드
@AutoConfigureMockMvc
public class LoggingInterationTest {

    @Autowired
    private MockMvc mockMvc;

    // 로그 캡처를 위한 ListAppender
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp(){
        // 테스트 시작전 root logger에 ListAppender 붙힘
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        listAppender = new ListAppender<>();
        listAppender.start();
        rootLogger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown(){
        // 테스트 종료 후, Appender를 제거하여 다른 테스트에 영향을 주지 않도록 함
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.detachAppender(listAppender);
    }

    @Test
    @DisplayName("API 호출 시 MdcLoggingFilter와 LoggingAspect가 함꼐 동작하여 TraceID가 포함된 로그 남김")
    void loggingFilterAndAspectInterationTest() throws Exception {
        // when : 가짜 HTTP 요청
        mockMvc.perform(get("/test-logging")).andExpect(status().isOk());

        // then : 캡쳐된 로그들 검증

        // 첫 로그("Request start")에서 TraceID 추출
        String firstLogMessage = listAppender.list.get(0).getFormattedMessage();
        assertThat(firstLogMessage).startsWith("Request start with Trace ID:");
        String traceId = firstLogMessage.substring(firstLogMessage.lastIndexOf(" ") + 1);
        assertThat(traceId).isNotBlank();   // Trace ID가 비었는지 확인

        // 모든 캡처된 log에 동일한 TraceID가 MDC에 포함하는지 확인
        for (ILoggingEvent event : listAppender.list) {
            assertThat(event.getMDCPropertyMap()).containsKey("traceId");

            assertThat(event.getMDCPropertyMap().get("traceId")).isEqualTo(traceId);
        }

        // Aspect가 남긴 로그가 존재하는지 확인
//        boolean aspectStartLogFound = listAppender.list.stream().anyMatch(event -> event.getFormattedMessage().contains("==> Method Start: TestService.getTestData()"));
//        boolean aspectEndLogFound = listAppender.list.stream().anyMatch(event -> event.getFormattedMessage().contains("<== Method End: TestService.getTestData()"));

        // Aspect가 남긴 로그가 존재하는지 확인
        boolean aspectStartLogFound = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains("==> Method Start: TestService.getTestData()"));
        log.info("aspectStartLogFound : {}", listAppender.list.get(0).getFormattedMessage());
        boolean aspectEndLogFound = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains("<== Method End: TestService.getTestData()"));

        System.out.println("===== Captured Logs =====");
        listAppender.list.forEach(event -> System.out.println(event.getFormattedMessage()));
        System.out.println("===== Captured Logs =====");

        assertThat(aspectStartLogFound).isTrue();
        assertThat(aspectEndLogFound).isTrue();
        // 최소 4개 로그 찍혔는지 확인(request start, Method start, Method End, Request Finished)
        assertThat(listAppender.list.size()).isGreaterThanOrEqualTo(4);
    }
}
