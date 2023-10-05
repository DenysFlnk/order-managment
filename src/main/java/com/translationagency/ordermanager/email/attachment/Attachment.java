package com.translationagency.ordermanager.email.attachment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Setter
@Getter
public class Attachment {

    private String fileName;

    private FileType type;

    @Getter(AccessLevel.PRIVATE)
    private byte[] content;

    public Attachment(MultipartFile file) {
        this.fileName = file.getOriginalFilename();
        this.type = FileType.getFileType(file.getContentType());
        try {
            this.content = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e); //TODO exception
        }
    }

    public String getContentBase64Encode() {
        return Base64.getEncoder().encodeToString(getContent());
    }
}
