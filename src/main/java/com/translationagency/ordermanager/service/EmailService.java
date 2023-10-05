package com.translationagency.ordermanager.service;

import com.sendgrid.Response;
import com.translationagency.ordermanager.email.SendGridService;
import com.translationagency.ordermanager.to.email.EmailTo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private SendGridService sendGridService;

    private FileService fileService;

    public Response sendEmailWithAttachment(EmailTo email, List<MultipartFile> files) {
        return sendGridService.sendEmail(email, fileService.getAttachments(files));
    }
}
