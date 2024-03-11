package org.scaler.ecomemailservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.scaler.ecomemailservice.dtos.SendEmailEventDto;
import org.scaler.ecomemailservice.util.EmailUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SendEmailEventConsumer {

    private final ObjectMapper objectMapper;

    @Value("${email.from.password}")
    private String emailPassword;

    private final Logger logger;

    public SendEmailEventConsumer(ObjectMapper objectMapper, Logger logger) {
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void handleSendEmailEvent(String message) throws JsonProcessingException {
        if (logger.isDebugEnabled())
            logger.debug("Event received on kafka consumer.");
        SendEmailEventDto sendEmailEventDto = objectMapper.readValue(message, SendEmailEventDto.class);

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //trust ssl

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmailEventDto.getFrom(), emailPassword);
            }
        };

        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session, sendEmailEventDto.getTo(), sendEmailEventDto.getSubject(),
                sendEmailEventDto.getBody(), logger);
    }
}
