package ro.msg.learning.shop.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.*;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "location")
@Getter
@Setter
@RequiredArgsConstructor
public class LocationConfiguration {
    private final StockRepository stockRepository;
    private StrategyType strategy = StrategyType.SINGLE;
    private String apiHost;
    private String apiKey;
    private Long apiTimeout;

    @Bean
    @ConditionalOnProperty(name = "location.strategy", havingValue = "single")
    public LocationStrategy locationStrategy() {
        return new SingleLocationStrategy(stockRepository);
    }

    @Bean
    @ConditionalOnProperty(name = "location.strategy", havingValue = "most_abundant")
    public LocationStrategy mostAbundantStrategy() {
        return new MostAbundantStrategy(stockRepository);
    }

    @Bean
    @Autowired
    @ConditionalOnProperty(name = "location.strategy", havingValue = "greedy")
    public LocationStrategy greedyStrategy(RestTemplate restTemplate, LocationRepository locationRepository) {
        return new GreedyStrategy(stockRepository, locationRepository, restTemplate, this);
    }

    @Bean
    @ConditionalOnProperty(name = "location.strategy", havingValue = "greedy")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(apiHost))
                .setConnectTimeout(Duration.ofSeconds(apiTimeout))
                .setReadTimeout(Duration.ofSeconds(apiTimeout))
                .build();
    }
}
