package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.model.entities.Address;
import ro.msg.learning.shop.model.entities.Location;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Address> getAllLocationAddresses() {
        return locationRepository.findAll().stream().map(Location::getAddress).toList();
    }
}
