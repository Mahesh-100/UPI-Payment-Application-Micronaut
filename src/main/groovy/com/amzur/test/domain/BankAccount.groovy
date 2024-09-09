package com.amzur.test.domain

import grails.gorm.annotation.Entity

import groovy.transform.ToString

@Entity
@ToString(includeNames = true)
class BankAccount {

    Long id
    String bankName
    String accountNumber
    BigDecimal transactionLimit
    BigDecimal amount
    String upiPin
    Boolean isPrimary = false // Default to false


    static belongsTo = [user:UserDomain]
    static constraints = {
        bankName nullable: false, blank: false
        transactionLimit nullable: false, blank: false
        accountNumber nullable: false, blank: false, unique: true
        amount nullable: false, min: 0.0G // Ensures amount is non-negative
        upiPin nullable: false, blank: false, matches: "\\d{4}" // UPI_PIN must be exactly 4 digits

    }

    static mapping = {
        id generator: 'identity'
        amount scale: 2 // Ensures that amount is stored with two decimal places
    }
}
