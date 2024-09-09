package com.amzur.test.handler

class UserNotFound extends RuntimeException {

    UserNotFound(String msg) {
        super(msg)
    }
}
