package com.translationagency.ordermanager.exception_handling;

import com.translationagency.ordermanager.exception_handling.error.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.translationagency.ordermanager.exception_handling.error.ErrorType.*;


@RestControllerAdvice
public class RestExceptionInfoHandler extends AbstractExceptionInfoHandler{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true, DATA_CONFLICT);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> bindValidationError(HttpServletRequest req, BindException e) {
        String[] details = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, BAD_DATA, details);
    }

    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> validationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> authenticationError(HttpServletRequest req, AuthenticationException e) {
        return logAndGetErrorInfo(req, e, false, UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> authorizationError(HttpServletRequest req, AccessDeniedException e) {
        return logAndGetErrorInfo(req, e, false, FORBIDDEN);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorInfo> userCredentialsError(HttpServletRequest req, AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            return logAndGetErrorInfo(req, new BadCredentialsException("Wrong password"), false, AUTH_ERROR);
        }
        return logAndGetErrorInfo(req, e, false, AUTH_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorInfo> noHandlerFoundError(HttpServletRequest req, NoHandlerFoundException e) {
        return logAndGetErrorInfo(req, e, false, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }
}