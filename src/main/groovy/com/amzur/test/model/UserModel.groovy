package com.amzur.test.model

import com.amzur.test.domain.UserDomain

class UserModel {
    Long id
    String firstName
    String lastName
    String mobileNumber
    String email
    String address
    String pin
    List<BankAccountModel> accounts

    // Method to convert UserDomain to UserModel
    static UserModel toModel(UserDomain userDomain) {
        return new UserModel(
                id: userDomain.id,
                firstName: userDomain.firstName,
                lastName: userDomain.lastName,
                mobileNumber: userDomain.mobileNumber,
                email: userDomain.email,
                address: userDomain.address,
                pin: userDomain.pin,
                accounts: userDomain.accounts.collect { BankAccountModel.toModel(it) }
        )
    }

    // Method to convert UserModel to UserDomain
    static UserDomain toDomain(UserModel userModel) {
        def userDomain = new UserDomain(
                id: userModel.id,
                firstName: userModel.firstName,
                lastName: userModel.lastName,
                mobileNumber: userModel.mobileNumber,
                email: userModel.email,
                address: userModel.address,
                pin: userModel.pin
        )



        return userDomain
    }
}

