package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }
}
