package org.trigear.exception.user;

public class UserNotFound extends Exception{
    public UserNotFound() {
        super();
    }

    public UserNotFound(String message) {
        super(message);
    }
}
