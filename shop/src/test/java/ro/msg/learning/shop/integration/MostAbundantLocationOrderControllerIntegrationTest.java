package ro.msg.learning.shop.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.dto.AddressDto;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.exception.NoLocationFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.msg.learning.shop.helper.test.TestDataHelper.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties", properties = {"location.strategy=most_abundant"})
@ActiveProfiles("test")
class MostAbundantLocationOrderControllerIntegrationTest {
    @Autowired
    public MockMvc mvc;

    @Autowired
    public ObjectMapper mapper;

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
        var address = AddressDto.builder()
                .country("USA")
                .city("Los Angeles")
                .county("Los Angeles")
                .street("5723 Morgan Ave")
                .build();
        var products = List.of(
                OrderDetailDto.builder().productId(rtx3080.getId()).quantity(15).build(),
                OrderDetailDto.builder().productId(samsungS22Ultra.getId()).quantity(2).build(),
                OrderDetailDto.builder().productId(rx6900.getId()).quantity(3).build(),
                OrderDetailDto.builder().productId(rogZephyrus.getId()).quantity(2).build()
        );
        var newOrder = OrderDto.builder()
                .createdAt(LocalDateTime.now())
                .deliveryAddress(address)
                .orderedProducts(products)
                .customer(CustomerDto.builder().customerId(admin.getId()).build()).build();
        var ow = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = ow.writeValueAsString(newOrder);
        var result = mvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        var orderDto = mapper.readValue(result.getResponse().getContentAsString(), OrderDto.class);
        var productIdToLocationId =
                orderDto.getOrderedProducts().stream().collect(Collectors.toMap(OrderDetailDto::getProductId, OrderDetailDto::getLocationId));
        Arrays.asList(rtx3080, samsungS22Ultra).forEach(product ->
                assertThat(productIdToLocationId).containsEntry(product.getId(), amazon.getId())
        );
        assertThat(productIdToLocationId).containsEntry(rogZephyrus.getId(), eMAG.getId())
                .containsEntry(rx6900.getId(), pcGarage.getId());
    }

    @Test
    void testCreateOrder_withoutEnoughStock_shouldThrowException() throws Exception {
        var address = AddressDto.builder()
                .country("USA")
                .city("Los Angeles")
                .county("Los Angeles")
                .street("5723 Morgan Ave")
                .build();
        var products = List.of(
                OrderDetailDto.builder().productId(rtx3080.getId()).quantity(60).build()
        );
        var newOrder = OrderDto.builder()
                .createdAt(LocalDateTime.now())
                .deliveryAddress(address)
                .orderedProducts(products)
                .customer(CustomerDto.builder().customerId(admin.getId()).build()).build();
        var ow = mapper.writer().withDefaultPrettyPrinter();
        var requestJson = ow.writeValueAsString(newOrder);
        mvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(NoLocationFoundException.class));
    }
}
