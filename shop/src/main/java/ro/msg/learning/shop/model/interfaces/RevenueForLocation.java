package ro.msg.learning.shop.model.interfaces;

import java.math.BigDecimal;

public interface RevenueForLocation {
    Integer getLocationId();

    BigDecimal getRevenue();
}
