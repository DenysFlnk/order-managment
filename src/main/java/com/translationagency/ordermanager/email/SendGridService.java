package com.translationagency.ordermanager.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.translationagency.ordermanager.email.attachment.Attachment;
import com.translationagency.ordermanager.to.email.EmailTo;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SendGridService {

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    private SendGrid sendGrid;

    private final SendGridMail sendGridMail;

    public SendGridService(SendGridMail sendGridMail) {
        this.sendGridMail = sendGridMail;
    }

    @PostConstruct
    public void configure() {
        sendGrid = new SendGrid(apiKey);
    }

    public Response sendEmail(EmailTo email, @Nullable List<Attachment> files) {
        try {
            return sendGrid.api(createRequest(email, files));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO exception handling
        }
    }

    private Request createRequest(EmailTo email, @Nullable List<Attachment> files) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(sendGridMail.createMail(email, files));
        return request;
    }
}
