package com.translationagency.ordermanager.email.attachment;

import com.translationagency.ordermanager.exception_handling.error.AttachmentException;

public enum FileType {
    JPEG("image/jpeg"),
    PNG("image/png"),
    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("application/msword"),
    TXT("text/plain");

    private final String typeName;

    FileType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static FileType getFileType(String typeName) {
        for (FileType type : values()) {
            if (type.getTypeName().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new AttachmentException(typeName + " is not supported");
    }
}
