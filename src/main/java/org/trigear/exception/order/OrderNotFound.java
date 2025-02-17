package org.trigear.exception.order;

public class OrderNotFound extends Exception{
    public OrderNotFound() {
        super();
    }

    public OrderNotFound(String message) {
        super(message);
    }
}
