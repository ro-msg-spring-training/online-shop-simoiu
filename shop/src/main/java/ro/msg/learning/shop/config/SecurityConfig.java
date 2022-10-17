package ro.msg.learning.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    @Profile("with-basic")
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/orders/**", "/products/**", "/stocks/**").authenticated()
                .antMatchers("/", "/**").permitAll()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    @Profile("with-form")
    protected SecurityFilterChain configureWithForm(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/orders/**", "/products/**", "/stocks/**").authenticated()
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin();
        return http.build();
    }

    @Bean
    @Profile("test")
    public SecurityFilterChain testSecurityChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}