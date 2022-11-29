package ro.msg.learning.shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import ro.msg.learning.shop.service.mail.EmailService;

@ConfigurationProperties(prefix = "spring.mail")
@ConstructorBinding
public record EmailConfiguration(
        String subject,
        EmailService.EmailType templateType
) {
}
