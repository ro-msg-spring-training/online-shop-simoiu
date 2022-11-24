package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotEmpty
    private String country;
    @NotEmpty
    private String city;
    @NotEmpty
    private String county;
    @NotEmpty
    private String street;
}