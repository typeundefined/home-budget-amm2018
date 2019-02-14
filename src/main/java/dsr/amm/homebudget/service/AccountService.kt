package dsr.amm.homebudget.service

import dsr.amm.homebudget.OrikaMapper
import dsr.amm.homebudget.controller.exception.ForbiddenException
import dsr.amm.homebudget.controller.exception.NotFoundException
import dsr.amm.homebudget.data.dto.*
import dsr.amm.homebudget.data.entity.Account
import dsr.amm.homebudget.data.entity.Currency
import dsr.amm.homebudget.data.entity.Person
import dsr.amm.homebudget.data.entity.tx.DepositTx
import dsr.amm.homebudget.data.entity.tx.Transaction
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx
import dsr.amm.homebudget.data.repository.AccountRepository
import dsr.amm.homebudget.data.repository.CurrencyRepository
import dsr.amm.homebudget.data.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.function.Supplier

@Service
open class AccountService @Autowired constructor(
        open val repository: AccountRepository,
        open val currencyRepository: CurrencyRepository,
        open val withdrawalTxRepository: TransactionRepository<WithdrawalTx>,
        open val depositRepo: TransactionRepository<DepositTx>,
        open val transactionRepository: TransactionRepository<Transaction>,
        open val authService: AuthService,
        open val mapper: OrikaMapper
) {

    open val myAccounts: List<AccountDTO>
        @Transactional
        get() {
            val accList = repository.findByOwner(authService.myself)
            return mapper.mapAsList(accList, AccountDTO::class.java)
        }

    private// TODO implement me when authentication gets ready
    val myself: Person?
        get() = null

    @Transactional
    open fun create(newAcc: AccountNewDTO): AccountDTO {
        val account = mapper.map<AccountNewDTO, Account>(newAcc, Account::class.java).also {
            it.owner = myself
            it.currentValue = BigDecimal.ZERO
            it.createDate = OffsetDateTime.now()
            it.owner = authService.myself


            // TODO: move to the mapper!
            it.currency = getCurrency(newAcc.currency)
        }

        val res = repository.save(account)
        return mapper.map(res, AccountDTO::class.java)
    }

    @Transactional
    open fun deposit(accountId: Long?, tx: DepositTxDTO): TransactionDTO? {
        return null
    }

    private fun getCurrency(currency: CurrencyIdDTO) = currencyRepository
            .findById(currency.code)
            .orElse(null) ?: throw NotFoundException("No such currency found")


    fun withdraw(accountId: Long, tx: WithdrawalTxDTO): TransactionDTO {
        val acc = getAccount(accountId)

        val transaction = mapper.map<WithdrawalTxDTO, WithdrawalTx>(tx, WithdrawalTx::class.java)
        transaction.createDate = OffsetDateTime.now()
        transaction.src = acc

        val value = acc.currentValue

        val amount = convertToCurrency(transaction.amount, transaction.currency, acc.currency)

        val newValue = value.subtract(amount)
        acc.currentValue = newValue
        transaction.newValue = newValue

        val txResult = withdrawalTxRepository.save(transaction)
        repository.save(acc)

        return mapper.map(txResult, TransactionDTO::class.java)
    }

    private fun getAccount(accountId: Long, needLock: Boolean = true): Account {
        val msg = "No account found [id = $accountId]"
        val acc = if (needLock) {
            repository.findByIdForUpdate(accountId) ?: throw NotFoundException(msg)
        } else {
            repository.findById(accountId).orElse(null) ?: throw NotFoundException(msg)
        }

        ensureMine(acc)
        return acc
    }

    private fun convertToCurrency(amount: BigDecimal, from: Currency, to: Currency): BigDecimal {
        // FIXME add support of currency conversions
        return amount
    }

    private fun ensureMine(acc: Account) {
        if (acc.owner.id == authService.myself.id)
            return
        else
            throw ForbiddenException("This is not your account!")
    }

    fun getTransactionsByAccount(pageable: Pageable, accountId: Long): Page<TransactionDTO> = transactionRepository
            .findAllByAccount(pageable, getAccount(accountId, false))
            .map { t: Transaction -> mapper.map<Transaction, TransactionDTO>(t, TransactionDTO::class.java) }
}
