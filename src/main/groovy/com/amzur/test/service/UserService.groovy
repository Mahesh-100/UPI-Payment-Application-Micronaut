package com.amzur.test.service

import com.amzur.test.constants.ApplicationConstants
import com.amzur.test.domain.UserDomain
import com.amzur.test.handler.UserNotFound
import com.amzur.test.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class UserService {

    @Transactional
    def createUser(UserModel userModel) {
        // Convert UserModel to UserDomain using the toDomain method
        UserDomain userDomain = UserModel.toDomain(userModel)

        // Save the userDomain instance, ensuring it is persisted in the database
        userDomain = userDomain.save(flush: true)

        // Return the saved UserDomain or convert it to UserModel if needed
        return UserModel.toModel(userDomain)
    }


    @Transactional
    def findUser(String mobileNumber, String pin) {
        def userDomain = UserDomain.findByMobileNumberAndPin(mobileNumber, pin)

        if (!userDomain) {
            // You can throw a custom exception if the user is not found
            throw new UserNotFound(ApplicationConstants.USER_NOT_FOUND)
        }

        // Return the UserModel (or specific fields like firstName)
        return UserModel.toModel(userDomain)
    }

}
