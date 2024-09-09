package com.amzur.test.controller

import com.amzur.test.service.TransactionService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.HttpStatus

import javax.inject.Inject


@Controller('/transactions')
class TransactionController {

    @Inject
    TransactionService transactionService

    @Get('/{accountId}')
    def getAllTransByAccId(@PathVariable Long accountId) {
        try {
            def result = transactionService.getTransactionByAccId(accountId)
            if (result.transactions.isEmpty()) {
                return HttpResponse.status(HttpStatus.NOT_FOUND).body([
                        message: result.message,
                        transactions: result.transactions
                ])
            }
            return HttpResponse.ok(result)
        } catch (Exception e) {
            return HttpResponse.serverError([
                    message: "An error occurred: ${e.message}"
            ])
        }
    }
}
