package com.amzur.test.controller

import com.amzur.test.model.UserModel
import com.amzur.test.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpResponse

import javax.inject.Inject

@Controller('/users')
class UserController {

    @Inject
    UserService userService


    @Post('/register')
    @Status(HttpStatus.CREATED)
    def addUser(@Body UserModel userModel) {
        try {
            def user = userService.createUser(userModel)
            if (user) {
                return HttpResponse.created(user)
            } else {
                return HttpResponse.badRequest("Failed to add user")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }
    @Post('/login')
    def login(@Body UserModel userModel){
        return userService.findUser(userModel.mobileNumber,userModel.pin)
    }

}
