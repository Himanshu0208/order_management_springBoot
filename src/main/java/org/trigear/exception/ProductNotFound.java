package org.trigear.exception;

public class ProductNotFound extends Exception {
    public ProductNotFound() {
        super();
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
