package com.abcbank.redis2;

import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.service.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventPublisher eventPublisher;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testLoginEndpointPublishesEvent() throws Exception {
        mockMvc.perform(get("/products/login"))
               .andExpect(status().isOk())
               .andExpect(content().string("Login event published"));

        verify(eventPublisher).publishLoginEvent(any(LoginEvent.class));
    }
}
