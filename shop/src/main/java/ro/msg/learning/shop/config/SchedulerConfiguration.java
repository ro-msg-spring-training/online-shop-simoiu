package ro.msg.learning.shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "schedule")
@ConstructorBinding
public record SchedulerConfiguration(
        String saveRevenuesCron
) {
}
