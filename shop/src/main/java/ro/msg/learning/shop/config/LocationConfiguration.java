package ro.msg.learning.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategy.*;

import java.time.Duration;

@ConfigurationProperties(prefix = "location")
@ConstructorBinding
public record LocationConfiguration(
        StrategyType strategy,
        String apiHost,
        String apiKey,
        Long apiTimeout
) {
    @Bean
    @Autowired
    @ConditionalOnProperty(name = "location.strategy", havingValue = "single")
    public LocationStrategy locationStrategy(StockRepository stockRepository) {
        return new SingleLocationStrategy(stockRepository);
    }

    @Bean
    @Autowired
    @ConditionalOnProperty(name = "location.strategy", havingValue = "most_abundant")
    public LocationStrategy mostAbundantStrategy(StockRepository stockRepository) {
        return new MostAbundantStrategy(stockRepository);
    }

    @Bean
    @Autowired
    @ConditionalOnProperty(name = "location.strategy", havingValue = "greedy")
    public LocationStrategy greedyStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        return new GreedyStrategy(stockRepository, locationRepository);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(apiHost))
                .setConnectTimeout(Duration.ofSeconds(apiTimeout))
                .setReadTimeout(Duration.ofSeconds(apiTimeout))
                .build();
    }
}
