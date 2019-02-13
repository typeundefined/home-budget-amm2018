package dsr.amm.homebudget.controller

import dsr.amm.homebudget.controller.exception.ApiException
import dsr.amm.homebudget.data.dto.*
import dsr.amm.homebudget.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * Created by knekrasov on 10/15/2018.
 */
@RestController
@RequestMapping("/account")
open class AccountController @Autowired constructor(open val accountService: AccountService) {

    val current: List<AccountDTO>
        @RequestMapping(method = [GET])
        get() = accountService.myAccounts

    @RequestMapping(method = [POST])
    @ResponseStatus(CREATED)
    fun createAccount(@Valid @RequestBody newAcc: AccountNewDTO): AccountDTO = accountService.create(newAcc)

    @RequestMapping(value = ["/{id}/transactions"], method = [POST])
    @ResponseStatus(CREATED)
    fun addTransaction(@RequestBody @Valid tx: TransactionDTO, @PathVariable("id") accountId: Long): TransactionDTO? {
        if (tx is DepositTxDTO) {
            return accountService.deposit(accountId, tx)
        } else if (tx is WithdrawalTxDTO) {
            return accountService.withdraw(accountId, tx)
        }
        throw ApiException("Unsupported transaction type submitted")
    }

    @RequestMapping(value = ["/{id}/transactions"], method = [GET])
    fun getTransactions(pageable: Pageable, @PathVariable("id") accountId: Long) =
            accountService.getTransactionsByAccount(pageable, accountId)
}
