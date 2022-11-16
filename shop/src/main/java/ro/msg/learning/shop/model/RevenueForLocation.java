package ro.msg.learning.shop.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RevenueForLocation {
    private String locationId;
    private BigDecimal revenue;
}
