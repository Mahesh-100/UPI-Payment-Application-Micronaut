package com.amzur.test.service

import com.amzur.test.domain.BankAccount
import com.amzur.test.domain.Transaction
import com.amzur.test.model.TransactionModel
import grails.gorm.transactions.Transactional
import javassist.NotFoundException

import javax.inject.Singleton

@Singleton
class TransactionService {


    @Transactional
    def getTransactionByAccId(Long accountId) {
        // Fetch the account by ID
        def account = BankAccount.findById(accountId)

        // Check if the account exists
        if (!account) {
            throw new NotFoundException("Account with ID ${accountId} not found")
        }

        // Fetch transactions related to the account
        def transactions = Transaction.findAllByAccount(account)
        def transactionModels = TransactionModel.toModelList(transactions)

        // Check if any transactions are found
        if (!transactions) {
            return [
                    message: "No transactions found for account with ID ${accountId}",
                    transactions: []
            ]
        }

        // Return the list of transactions
        return [
                message: "Transactions retrieved successfully",
                transactions: transactionModels
        ]
    }

}
