package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.mapper.CustomerMapper;
import ro.msg.learning.shop.service.CustomUserDetailService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    private final CustomUserDetailService userDetailsService;
    private final CustomerMapper customerMapper;

    @GetMapping(value = "/{userName}")
    public CustomerDto getCustomerByUsername(@PathVariable String userName) {
        return customerMapper.mapToDto(userDetailsService.getByUsername(userName));
    }
}
