package org.trigear.exception.order;

public class OrderDetailsNotFound extends Exception{
    public OrderDetailsNotFound() {
        super();
    }

    public OrderDetailsNotFound(String message) {
        super(message);
    }
}
