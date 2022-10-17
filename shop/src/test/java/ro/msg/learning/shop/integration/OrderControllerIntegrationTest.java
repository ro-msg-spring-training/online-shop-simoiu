package ro.msg.learning.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.exception.NoLocationFoundException;
import ro.msg.learning.shop.model.Address;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class OrderControllerIntegrationTest {
    @Autowired
    public MockMvc mvc;

    static ObjectMapper mapper;

    @BeforeAll
    public static void beforeAll() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void beforeEachTest() throws Exception {
        mvc.perform(get("/test/populate")).andExpect(status().isOk());
    }

    @AfterEach
    public void after() throws Exception {
        mvc.perform(get("/test/clear")).andExpect(status().isOk());
    }

    @Test
    void testCreateOrder_withEnoughStock_shouldCreateOrder() throws Exception {
        var address = Address.builder()
                .country("USA")
                .city("Los Angeles")
                .county("Los Angeles")
                .street("5723 Morgan Ave")
                .build();
        var products = List.of(
                OrderDetailDto.builder().productId(1).quantity(6).build(),
                OrderDetailDto.builder().productId(4).quantity(2).build(),
                OrderDetailDto.builder().productId(3).quantity(3).build(),
                OrderDetailDto.builder().productId(7).quantity(11).build()
        );
        var newOrder = OrderDto.builder()
                .createdAt(LocalDateTime.now())
                .deliveryAddress(address)
                .orderedProducts(products)
                .customerId(1).build();
        var ow = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = ow.writeValueAsString(newOrder);
        mvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testCreateOrder_withoutEnoughStock_shouldThrowException() throws Exception {
        var address = Address.builder()
                .country("USA")
                .city("Los Angeles")
                .county("Los Angeles")
                .street("5723 Morgan Ave")
                .build();
        var products = List.of(
                OrderDetailDto.builder().productId(1).quantity(60).build()
        );
        var newOrder = OrderDto.builder()
                .createdAt(LocalDateTime.now())
                .deliveryAddress(address)
                .orderedProducts(products)
                .customerId(1).build();
        var ow = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = ow.writeValueAsString(newOrder);
        mvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(NoLocationFoundException.class));
    }
}
