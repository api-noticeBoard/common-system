package com.portfolio.syscomm.logging;

import com.portfolio.syscomm.TestController;
import com.portfolio.syscomm.TestService;
import com.portfolio.syscomm.logging.fillter.MdcLoggingFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@Import({MdcLoggingFilter.class,
        TestService.class
})
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /test-logging 요청 시 TestController가 실제 TestService를 호출하여 200 OK와 데이터를 반환")
    void controllerShouldReturn2000kWithData() throws Exception{
        mockMvc.perform(get("/test-logging"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is test data."));
    }
}
