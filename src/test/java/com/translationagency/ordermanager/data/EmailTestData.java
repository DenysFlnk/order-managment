package com.translationagency.ordermanager.data;

import com.translationagency.ordermanager.email.attachment.FileType;
import com.translationagency.ordermanager.to.email.EmailTo;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailTestData {

    public static EmailTo email = new EmailTo(5, "filonenko.denys94@gmail.com", "yoruhide@gmail.com",
            "John", "Order #5", "Translate from english to ukrainian");

    public static MockMultipartFile file1 = new MockMultipartFile("file", "file1.txt", FileType.TXT.getTypeName(),
            "first file content".getBytes());

    public static MockMultipartFile file2 = new MockMultipartFile("file", "file2.pdf", FileType.PDF.getTypeName(),
            "second file content".getBytes());

    public static MultiValueMap<String, String> allParams = new MultiValueMapAdapter<>(getParamsFromTo());

    private static Map<String, List<String>> getParamsFromTo() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("orderId", List.of(String.valueOf(email.getOrderNumber())));
        map.put("senderEmail", List.of(email.getSenderEmail()));
        map.put("targetEmail", List.of(email.getTargetEmail()));
        map.put("translatorName", List.of(email.getTranslatorName()));
        map.put("subject", List.of(email.getSubject()));
        map.put("addInfo", List.of(email.getAddInfo()));

        return map;
    }
}
