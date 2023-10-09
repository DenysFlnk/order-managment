package com.translationagency.ordermanager.exception_handling;

import com.translationagency.ordermanager.exception_handling.error.AttachmentException;
import com.translationagency.ordermanager.exception_handling.error.EmailException;
import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.translationagency.ordermanager.exception_handling.error.ErrorType.EMAIL_ERROR;

@ControllerAdvice
public class ControllerExceptionInfoHandler extends AbstractExceptionInfoHandler{

    @ExceptionHandler({EmailException.class, AttachmentException.class})
    public ResponseEntity<ErrorInfo> emailError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, EMAIL_ERROR);
    }
}
