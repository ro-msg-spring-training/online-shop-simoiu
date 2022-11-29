package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.NoSuchElementFoundException;
import ro.msg.learning.shop.model.entities.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var customer = getByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(customer.getUsername())
                .password(customer.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    public Customer getByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Customer getById(String id) {
        return getCustomerEntityById(id);
    }

    private Customer getCustomerEntityById(String id) {
        var customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new NoSuchElementFoundException("There is no customer with id=%s".formatted(id));
        }
        return customer.get();
    }
}
