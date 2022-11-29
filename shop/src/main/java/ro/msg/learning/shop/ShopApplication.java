package ro.msg.learning.shop;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import ro.msg.learning.shop.config.EmailConfiguration;
import ro.msg.learning.shop.config.LocationConfiguration;
import ro.msg.learning.shop.config.SchedulerConfiguration;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({LocationConfiguration.class, SchedulerConfiguration.class, EmailConfiguration.class})
@EnableMongock
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
