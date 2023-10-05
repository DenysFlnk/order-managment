package com.translationagency.ordermanager.email;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.translationagency.ordermanager.email.attachment.Attachment;
import com.translationagency.ordermanager.to.email.EmailTo;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SendGridMail {

    @Value("${spring.sendgrid.template}")
    private String templateId;

    private Mail mail;

    protected String createMail(EmailTo email, @Nullable List<Attachment> files) throws IOException {
        mail = new Mail();
        Email sender = new Email(email.getSenderEmail());
        mail.setFrom(sender);
        mail.setTemplateId(templateId);

        Personalization personalization = createPersonalization(email);
        mail.addPersonalization(personalization);

        if (files != null) {
            addAttachments(files);
        }

        return mail.build();
    }

    private void addAttachments(List<Attachment> files) {
        for (Attachment file : files) {
            Attachments attachment = new Attachments();
            attachment.setFilename(file.getFileName());
            attachment.setType(file.getType().getTypeName());
            attachment.setContent(file.getContentBase64Encode());
            mail.addAttachments(attachment);
        }
    }

    private Personalization createPersonalization(EmailTo email) {
        Personalization personalization = new Personalization();
        Email target = new Email(email.getTargetEmail());
        personalization.addTo(target);
        personalization.addDynamicTemplateData("subject", email.getSubject());
        personalization.addDynamicTemplateData("orderNumber", email.getOrderNumber());
        personalization.addDynamicTemplateData("translatorName", email.getTranslatorName());
        personalization.addDynamicTemplateData("addInfo", email.getAddInfo());
        return personalization;
    }
}
