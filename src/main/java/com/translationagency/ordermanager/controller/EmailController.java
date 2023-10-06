package com.translationagency.ordermanager.controller;

import com.sendgrid.Response;
import com.translationagency.ordermanager.service.EmailService;
import com.translationagency.ordermanager.to.email.EmailTo;
import com.translationagency.ordermanager.util.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = EmailController.EMAIL_URL)
@Slf4j
@AllArgsConstructor
public class EmailController {

    public static final String EMAIL_URL = "translators/email";

    private EmailService emailService;

    @PostMapping()
    public ResponseEntity<String> sendEmailWithAttachments(@RequestParam(value = "file") List<MultipartFile> files,
                                                           @RequestParam Map<String, String> allParam) {
        log.info("sendEmailWithAttachments for order {}", allParam.get("orderId"));
        EmailTo email = EmailUtil.getToFromRequestParameters(allParam);
        Response response = emailService.sendEmailWithAttachment(email, files);
        return new ResponseEntity<>(response.getBody(), HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
