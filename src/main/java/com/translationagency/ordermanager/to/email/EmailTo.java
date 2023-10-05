package com.translationagency.ordermanager.to.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailTo {
    private int orderNumber;

    private String senderEmail;

    private String targetEmail;

    private String translatorName;

    private String subject;

    private String addInfo;

}
