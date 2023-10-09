package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.to.email.EmailTo;
import com.translationagency.ordermanager.util.validation.ValidationUtil;

import java.util.Map;

public class EmailUtil {

    public static EmailTo getToFromRequestParameters(Map<String, String> allParam) {
        EmailTo email = new EmailTo();
        email.setOrderNumber(Integer.parseInt(allParam.get("orderId")));
        email.setSenderEmail(allParam.get("senderEmail"));
        email.setTargetEmail(allParam.get("targetEmail"));
        email.setTranslatorName(allParam.get("translatorName"));
        email.setSubject(allParam.get("subject"));
        email.setAddInfo(allParam.get("addInfo"));

        ValidationUtil.checkIfEmailHasAnyNullField(email);

        return email;
    }
}
