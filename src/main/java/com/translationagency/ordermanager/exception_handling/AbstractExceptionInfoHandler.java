package com.translationagency.ordermanager.exception_handling;

import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import com.translationagency.ordermanager.exception_handling.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

@Slf4j
public abstract class AbstractExceptionInfoHandler {

    protected static ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, Exception e,
                                                               boolean logStackTrace, ErrorType errorType,
                                                               String... details) {
        Throwable rootCause = logAndGetRootCause(req, e, logStackTrace, errorType);
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(req.getRequestURL().toString(), errorType,
                        errorType.getTitle(),
                        details.length != 0 ? details : new String[]{getMessage(rootCause)})
                );
    }

    private static Throwable logAndGetRootCause(HttpServletRequest req, Exception e, boolean logStackTrace,
                                                ErrorType errorType) {
        Throwable rootCause = getRootCause(e);
        if (logStackTrace) {
            log.error("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

    private static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    private static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}
