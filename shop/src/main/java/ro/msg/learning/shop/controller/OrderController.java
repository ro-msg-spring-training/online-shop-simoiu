package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.config.LocationConfiguration;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.helper.RouteMatrixAPIHelper;
import ro.msg.learning.shop.mapper.AddressMapper;
import ro.msg.learning.shop.mapper.CustomerMapper;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.service.CustomUserDetailService;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.mail.EmailService;
import ro.msg.learning.shop.strategy.StrategyType;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final CustomUserDetailService customerService;
    private final LocationService locationService;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;
    private final CustomerMapper customerMapper;
    private final RouteMatrixAPIHelper routeMatrixAPIHelper;
    private final LocationConfiguration locationConfiguration;
    private final EmailService emailService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        List<BigDecimal> distances = null;
        if (locationConfiguration.strategy() == StrategyType.GREEDY) {
            var allLocationsAddresses = locationService.getAllLocationAddresses();
            distances = routeMatrixAPIHelper.getDistancesFromLocations(orderDto.getDeliveryAddress(), addressMapper.mapAllToDto(allLocationsAddresses));
        }

        var createdOrder = orderMapper.mapToDto(orderService.createOrder(orderMapper.mapToEntity(orderDto), distances));
        var customer = customerService.getById(createdOrder.getCustomer().getCustomerId());
        createdOrder.setCustomer(customerMapper.mapToDto(customer));
        emailService.sendMail(createdOrder);
        return createdOrder;
    }
}
