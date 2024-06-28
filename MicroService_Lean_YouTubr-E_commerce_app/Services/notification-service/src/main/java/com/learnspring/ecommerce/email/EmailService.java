package com.learnspring.ecommerce.email;

import com.learnspring.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learnspring.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.learnspring.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    // Send payment success mail
    @Async
    public void sendPaymentSuccessEmail(
            String destinationMail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setTo("project1.mhs@gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationMail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s", destinationMail, templateName));
        }catch (MessagingException e) {
            log.warn("WARNING - Email could not be sent to " + destinationMail, e);
        }
    }

    // Send order confirmation mail
    @Async
    public void sendOrderConfirmationEmail(
            String destinationMail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        mimeMessageHelper.setTo("project1.mhs@gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("totalAmount", amount);
        model.put("orderReference", orderReference);
        model.put("products", products);

        Context context = new Context();
        context.setVariables(model);
        mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htmlTemplate, true);

            mimeMessageHelper.setTo(destinationMail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s", destinationMail, templateName));
        }catch (MessagingException e) {
            log.warn("WARNING - Email could not be sent to " + destinationMail, e);
        }
    }
}
