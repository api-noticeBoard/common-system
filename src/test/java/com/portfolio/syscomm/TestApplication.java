package com.portfolio.syscomm;

import com.portfolio.syscomm.config.SystemCommonAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SystemCommonAutoConfiguration.class) // 자동 설정을 여기서 로드
public class TestApplication {
}
