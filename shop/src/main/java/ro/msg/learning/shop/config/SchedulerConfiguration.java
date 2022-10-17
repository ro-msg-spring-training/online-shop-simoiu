package ro.msg.learning.shop.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "schedule")
@Getter
@Setter
@RequiredArgsConstructor
public class SchedulerConfiguration {
    private String saveRevenuesCron;
}
