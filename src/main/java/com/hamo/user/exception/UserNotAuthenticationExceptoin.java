package com.hamo.user.exception;

public class UserNotAuthenticationExceptoin extends RuntimeException {
    public UserNotAuthenticationExceptoin(String message) {
        super(message);
    }

    public UserNotAuthenticationExceptoin(Long userId) {
        super("User with ID " + userId + " is not authenticated.");
    }
}
