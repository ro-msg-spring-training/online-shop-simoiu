package ro.msg.learning.shop.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.config.LocationConfiguration;
import ro.msg.learning.shop.dto.AddressDto;
import ro.msg.learning.shop.dto.LocationRequestDto;
import ro.msg.learning.shop.dto.RouteMatrixDto;
import ro.msg.learning.shop.exception.RouteMatrixApiException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RouteMatrixAPIHelper {
    private final LocationConfiguration locationConfiguration;
    private final RestTemplate restTemplate;

    public List<BigDecimal> getDistancesFromLocations(AddressDto deliveryAddress, List<AddressDto> stockLocations) {
        var addressList = Stream.concat(Stream.of(deliveryAddress), stockLocations.stream()).toList();
        var request = LocationRequestDto.builder().locations(addressList).build();
        var response = restTemplate.postForObject("/routematrix?key=%s".formatted(locationConfiguration.apiKey()), request, RouteMatrixDto.class);
        if (response == null || response.getInfo() == null) {
            throw new RouteMatrixApiException("Unable to get any response from the API");
        }
        if (response.getInfo().getStatusCode() != 0) {
            throw new RouteMatrixApiException(String.join("\n", response.getInfo().getMessages()));
        }
        if (response.getDistance().size() != addressList.size()) {
            throw new RouteMatrixApiException("API returned %d elements instead of %d".formatted(response.getDistance().size(), addressList.size()));
        }
        return response.getDistance();
    }

}
