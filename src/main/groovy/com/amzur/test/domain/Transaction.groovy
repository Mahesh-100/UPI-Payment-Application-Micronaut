package com.amzur.test.domain

import grails.gorm.annotation.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.text.SimpleDateFormat


@Entity
@ToString(includeNames = true)
@EqualsAndHashCode
class Transaction {

    Long id
    Date date
    BigDecimal amount
    String recipient

    static belongsTo = [user:UserDomain ,account:BankAccount]
    static constraints = {
        date nullable: false

        amount nullable: false, min: 0.0G // Amount must be non-negative
        recipient nullable: false, blank: false
    }

    static mapping = {
        id generator: 'identity'
        amount scale: 2 // Store amount with two decimal places
    }


}
