package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import ro.msg.learning.shop.model.Address;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class LocationRequest {
    @JsonUnwrapped
    private List<Address> locations;
}
