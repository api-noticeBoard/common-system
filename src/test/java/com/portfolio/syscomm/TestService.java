package com.portfolio.syscomm;

import com.portfolio.syscomm.logging.aop.annotation.Loggable;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Loggable
    public String getTestData() {
        return "This is test data.";
    }
}