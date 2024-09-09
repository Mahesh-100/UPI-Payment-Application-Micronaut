package com.amzur.test.handler

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error

import javax.validation.ConstraintViolationException

@Controller
class CustomGlobalExceptionHandler {

    @Error(global = true, exception = ConstraintViolationException.class)
    HttpResponse<Map<String, String>> handleConstraintViolation(HttpRequest request, ConstraintViolationException ex) {
        Map<String, String> errors = [:]
        ex.constraintViolations.each { violation ->
            String fieldName = violation.propertyPath.toString()
            String errorMessage = violation.message
            errors.put(fieldName, errorMessage)
        }
        return HttpResponse.status(HttpStatus.BAD_REQUEST).body(errors)
    }


    @Error(global = true, exception = UserNotFound)
    HttpResponse<ErrorResponse> handleLoginUser(UserNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.code,
                "Not Found",
                ex.message
        )
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
}