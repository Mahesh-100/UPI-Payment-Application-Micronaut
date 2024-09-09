package com.amzur.test.domain

import grails.gorm.annotation.Entity

import groovy.transform.ToString

@Entity
@ToString(includeNames = true)

class UserDomain {

    Long id
    String firstName
    String lastName
    String mobileNumber
    String email
    String address
    String pin

    static hasMany = [accounts:BankAccount]

    static constraints = {
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        mobileNumber nullable: false, blank: false, unique: true, matches: "\\d{10}"
        email nullable: false, blank: false, unique: true, email: true
        address nullable: false, blank: false
        pin nullable: false, blank: false, matches: "\\d{6}"
    }

    static mapping = {
        id generator: 'identity'
    }
}
