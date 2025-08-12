package com.portfolio.syscomm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/test-logging")
    public ResponseEntity<String> testLogging() {
        return ResponseEntity.ok(testService.getTestData());
    }
}