package com.amzur.test.service

import com.amzur.test.domain.BankAccount
import com.amzur.test.domain.Transaction
import com.amzur.test.domain.UserDomain
import com.amzur.test.model.BankAccountModel
import com.amzur.test.model.TransactionModel
import grails.gorm.transactions.Transactional
import javassist.NotFoundException

import javax.inject.Inject
import javax.inject.Singleton
import java.text.SimpleDateFormat


@Singleton
class BankAccountService {

    @Inject
    UserService userService

    @Transactional
    def createBankAccount(BankAccountModel bankAccountModel) {

        BankAccount bank = BankAccountModel.toDomain(bankAccountModel)
        bank = bank.save()
        return BankAccountModel.toModel(bank)
    }

    @Transactional
    def getAllAccounts(Long userId) {
        def user = UserDomain.findById(userId)
        if (!user) {
            throw new RuntimeException("User not found with ID: ${userId}")
        }
        def banks = BankAccount.findAllByUser(user)

        // Convert each bank account domain instance to a BankAccountModel
        def bankList = banks.collect { bankAccount ->
            BankAccountModel.toModel(bankAccount)
        }
        return bankList
    }
    @Transactional
    def setPrimaryAccount(Long accountId) {
        // Find the account by ID
        def account = BankAccount.findById(accountId)
        if (!account) {
            throw new IllegalArgumentException("Account not found")
        }

        // Fetch the user associated with this account
        def user = account.user

        // Set all other accounts of this user as non-primary
        def allAccounts = BankAccount.findAllByUser(user)
        allAccounts.each { acc ->
            acc.isPrimary = acc.id == accountId
            acc.save(flush: true) // Persist changes
        }

        return [success: true, message: "Primary account set successfully."]
    }

    @Transactional
    def sendMoney(Long accountId, String upiPin, BigDecimal amount, String receiverMobileNumber) {
        // Step 1: Validate the sender's account and UPI PIN
        def senderAccount = BankAccount.findById(accountId)
        if (!senderAccount) {
            throw new NotFoundException("Sender's account not found")
        }
        if (senderAccount.upiPin != upiPin) {
            throw new IllegalArgumentException("Invalid UPI PIN")
        }
        if (senderAccount.amount < amount) {
            throw new IllegalArgumentException("Insufficient balance")
        }

        // Step 2: Find the receiver by mobile number and get their primary account
        def receiverUser = UserDomain.findByMobileNumber(receiverMobileNumber)
        if (!receiverUser) {
            throw new NotFoundException("Receiver not found")
        }
        def receiverPrimaryAccount = BankAccount.findByUserAndIsPrimary(receiverUser, true)
        if (!receiverPrimaryAccount) {
            throw new NotFoundException("Receiver's primary account not found")
        }

        // Step 3: Deduct the amount from the sender's account
        senderAccount.amount = senderAccount.amount - amount
        senderAccount.save(flush: true)

        // Step 4: Add the amount to the receiver's primary account
        receiverPrimaryAccount.amount = receiverPrimaryAccount.amount + amount
        receiverPrimaryAccount.save(flush: true)
        def transaction = new Transaction(
                date: new Date(),
                account: senderAccount,
                user: senderAccount.user,  // The user who owns the sender account
                amount: amount,
                recipient: receiverMobileNumber
        )
        transaction.save(flush: true)
        // Return a success message or object indicating the transaction is complete
        return TransactionModel.toModel(transaction)


    }

}