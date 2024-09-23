package com.shop.product_management.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message,Throwable e) {
        super(message);
    }
    public ResourceNotFoundException(Throwable e) {
        super(e);
    }
}