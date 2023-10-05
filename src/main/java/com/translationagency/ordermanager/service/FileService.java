package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.email.attachment.Attachment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<Attachment> getAttachments(List<MultipartFile> files) {
        List<Attachment> attachments = new ArrayList<>(files.size());

        for (MultipartFile file : files) {
            attachments.add(new Attachment(file));
        }

        return attachments;
    }
}
