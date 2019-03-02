package dsr.amm.homebudget.controller

import dsr.amm.homebudget.data.dto.*
import dsr.amm.homebudget.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.DELETE
import org.springframework.web.bind.annotation.RequestMethod.POST
import java.time.OffsetDateTime
import java.util.*

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
    fun addTransaction(
            @RequestBody @Valid tx: TransactionDTO,
            @PathVariable("id") accountId: Long
    ): TransactionDTO? =
            accountService.transaction(accountId, tx)

    @RequestMapping(value = ["/{id}/transactions"], method = [GET])
    fun getTransactions(
            pageable: Pageable,
            @PathVariable("id") accountId: Long,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: Optional<OffsetDateTime>,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: Optional<OffsetDateTime>
    ):Page<TransactionDTO> =
            accountService.getAccountTransactions(pageable, accountId, from, to)

    @RequestMapping(value = ["/{accountId}/transactions/{transactionId}"], method = [DELETE])
    fun deleteTransaction(
            @PathVariable("accountId") accountId: Long,
            @PathVariable("transactionId") transactionId: Long
    ): TransactionDTO =
        accountService.deleteTransaction(accountId, transactionId)
}
