package ro.msg.learning.shop.service.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ro.msg.learning.shop.config.EmailConfiguration;
import ro.msg.learning.shop.dto.OrderDto;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String EMAIL_TEXT_TEMPLATE_NAME = "email-plain-text.txt";
    private static final String EMAIL_HTML_TEMPLATE_NAME = "email-html-body.html";

    private final TemplateEngine templateEngine;
    private final JavaMailSender emailSender;
    private final EmailConfiguration emailConfiguration;

    @SneakyThrows
    public void sendMail(OrderDto orderDTO) {

        Context ctx = prepareContext(orderDTO);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(emailConfiguration.subject());
        message.setTo(orderDTO.getCustomer().getEmailAddress());

        switch (emailConfiguration.templateType()) {
            case PLAIN_TEXT -> {
                var textContent = templateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);
                message.setText(textContent);
            }
            case HTML_BODY -> {
                var htmlContent = templateEngine.process(EMAIL_HTML_TEMPLATE_NAME, ctx);
                message.setText(htmlContent, true);
            }
            case NONE -> {
                return;
            }
            default -> throw new IllegalStateException("Template type %s invalid".formatted(emailConfiguration.templateType()));
        }

        emailSender.send(mimeMessage);
    }


    private Context prepareContext(OrderDto orderDTO) {
        Context ctx = new Context();
        ctx.setVariable("orderDetails", orderDTO);
        return ctx;
    }

    public enum EmailType {
        PLAIN_TEXT, HTML_BODY, NONE;
    }
}
