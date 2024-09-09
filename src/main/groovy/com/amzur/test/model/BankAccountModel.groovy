package com.amzur.test.model

import com.amzur.test.domain.BankAccount
import com.amzur.test.domain.UserDomain

class BankAccountModel {
    Long accountId
    Long userId
    String bankName
    String accountNumber
    BigDecimal transactionLimit
    BigDecimal amount
    String upiPin


    static BankAccountModel toModel(BankAccount bankAccount) {
        new BankAccountModel(
                accountId: bankAccount.id,
                bankName: bankAccount.bankName,
                accountNumber: bankAccount.accountNumber,
                transactionLimit: bankAccount.transactionLimit,
                amount: bankAccount.amount,
                upiPin: bankAccount.upiPin,
                userId: bankAccount.userId
        )
    }
    static BankAccount toDomain(BankAccountModel model) {
        new BankAccount(
                id: model.accountId,
                bankName: model.bankName,
                accountNumber: model.accountNumber,
                transactionLimit: model.transactionLimit,
                amount: model.amount,
                upiPin: model.upiPin,
                user: UserDomain.findById(model.userId)
        )
    }
}
