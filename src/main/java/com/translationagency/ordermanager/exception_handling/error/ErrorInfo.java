package com.translationagency.ordermanager.exception_handling.error;

public record ErrorInfo (String url, ErrorType type, String typeMessage, String[] details){
}