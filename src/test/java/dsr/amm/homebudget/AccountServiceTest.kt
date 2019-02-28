package dsr.amm.homebudget

import dsr.amm.homebudget.controller.exception.NotFoundException
import dsr.amm.homebudget.data.dto.*
import dsr.amm.homebudget.data.entity.Account
import dsr.amm.homebudget.data.repository.AccountRepository
import dsr.amm.homebudget.service.AccountService
import dsr.amm.homebudget.service.AuthService
import dsr.amm.homebudget.service.CurrencyService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

import java.math.BigDecimal

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HomeBudgetApplication::class, TestConfig::class])
@ActiveProfiles("test")
open class AccountServiceTest {
    @Autowired
    private val accountService: AccountService? = null

    @Autowired
    private val currencyService: CurrencyService? = null

    @Autowired
    private val repository: AccountRepository? = null

    @Autowired
    private val authService: AuthService? = null

    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Before
    @Transactional
    @Commit
    open fun before() {
        val dto = RegistrationDTO()
        dto.username = "jpetrucci"
        dto.password = ""
        dto.fullName = ""

        authService!!.register(dto)

        val username = authService.myself.username
        val realUser = userDetailsService!!.loadUserByUsername(username)
        SecurityContextHolder
                .getContext().authentication = UsernamePasswordAuthenticationToken(realUser, realUser.password, realUser.authorities)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun canCreateAccount() {
        currencyService!!.create(currency("RUB"))

        val dto = AccountNewDTO().also { it.currency = currId("RUB") }
        accountService!!.create(dto)

        val accList = repository!!.findAll().toList()

        assertFalse(accList.isEmpty())
    }

    @Test(expected = NotFoundException::class)
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun missingCurrencyTriggersException() {
        val dto = AccountNewDTO().also { it.currency = currId("EUR") }
        accountService!!.create(dto)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun depositWorks() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }

        val acc = accountService!!.create(dto)
        accountService.transaction(acc.id!!, deposit(14.0))
        val accList = accountService.myAccounts

        assertEquals(BigDecimal.valueOf(14.0), accList[0].currentValue)

        val txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
        assertEquals(txHistory.totalElements, 1L)
        val tx = txHistory.iterator().next()
        assertEquals("deposit", tx.type)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun witdrawalWorks() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }

        val acc = accountService!!.create(dto)
        accountService.transaction(acc.id!!, deposit(20.0))
        accountService.transaction(acc.id!!, withdraw(11.0))
        val accList = accountService.myAccounts

        assertEquals(BigDecimal.valueOf(9.0), accList[0].currentValue)

        val txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
        assertEquals(txHistory.totalElements, 2L)
        val tx = txHistory
                .stream()
                .filter { t -> t.type == "withdrawal" }
                .findFirst().get()

        assertEquals("withdrawal", tx.type)
        assertTrue(tx is WithdrawalTxDTO)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun `insert withdraw transaction`() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }

        val acc = accountService!!.create(dto)
        accountService.transaction(acc.id!!, deposit(20.0))
        accountService.transaction(acc.id!!, deposit(21.0))
        accountService.transaction(acc.id!!, deposit(22.0))
        accountService.transaction(acc.id!!, insertWithdraw(11.0, OffsetDateTime.now().plusDays(1)))
        accountService.transaction(acc.id!!, insertWithdraw(12.0, OffsetDateTime.now().minusDays(2)))
        accountService.transaction(acc.id!!, insertWithdraw(13.0, OffsetDateTime.now()))
        val accList = accountService.myAccounts

        assertEquals(BigDecimal.valueOf(27.0), accList[0].currentValue)

        val txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals((-12.0).toBigDecimal(), txHistory[0].newValue)
        assertEquals(8.0.toBigDecimal(), txHistory[1].newValue)
        assertEquals(29.0.toBigDecimal(), txHistory[2].newValue)
        assertEquals(51.0.toBigDecimal(), txHistory[3].newValue)
        assertEquals(40.0.toBigDecimal(), txHistory[4].newValue)
        assertEquals(27.0.toBigDecimal(), txHistory[5].newValue)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun `update withdraw transaction`() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }
        val deposit1 = deposit(100.0)
        val deposit2 = deposit(200.0)
        val deposit3 = deposit(300.0)
        val withdraw1 = withdraw(10.0)
        val withdraw2 = withdraw(20.0)
        val withdraw3 = withdraw(30.0)

        val acc = accountService!!.create(dto)
        accountService.transaction(acc.id!!, deposit1)
        withdraw1.id = accountService.transaction(acc.id!!, withdraw1).id
        accountService.transaction(acc.id!!, withdraw2)
        accountService.transaction(acc.id!!, deposit2)
        withdraw3.id = accountService.transaction(acc.id!!, withdraw3).id
        accountService.transaction(acc.id!!, deposit3)
        val accList = accountService.myAccounts

        assertEquals(BigDecimal.valueOf(540.0), accList[0].currentValue)

        var txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals(100.0.toBigDecimal(), txHistory[0].newValue)
        assertEquals(90.0.toBigDecimal(), txHistory[1].newValue)
        assertEquals(70.0.toBigDecimal(), txHistory[2].newValue)
        assertEquals(270.0.toBigDecimal(), txHistory[3].newValue)
        assertEquals(240.0.toBigDecimal(), txHistory[4].newValue)
        assertEquals(540.0.toBigDecimal(), txHistory[5].newValue)

        withdraw1.amount = 40.0.toBigDecimal()
        withdraw3.amount = 60.0.toBigDecimal()
        accountService.transaction(acc.id!!, withdraw1)
        accountService.transaction(acc.id!!, withdraw3)

        txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals(100.0.toBigDecimal(), txHistory[0].newValue)
        assertEquals(60.0.toBigDecimal(), txHistory[1].newValue)
        assertEquals(40.0.toBigDecimal(), txHistory[2].newValue)
        assertEquals(240.0.toBigDecimal(), txHistory[3].newValue)
        assertEquals(180.0.toBigDecimal(), txHistory[4].newValue)
        assertEquals(480.0.toBigDecimal(), txHistory[5].newValue)

        assertEquals(BigDecimal.valueOf(480.0), accountService.myAccounts[0].currentValue)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun `insert deposit transaction`() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }

        val acc = accountService!!.create(dto)
        accountService.transaction(acc.id!!, deposit(200.0))
        accountService.transaction(acc.id!!, insertDeposit(2000.0, OffsetDateTime.parse("2001-12-03T10:15:30+03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
        accountService.transaction(acc.id!!, insertDeposit(800.0, OffsetDateTime.parse("2001-12-03T10:15:30+03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
        accountService.transaction(acc.id!!, deposit(1000.0))
        accountService.transaction(acc.id!!, insertDeposit(1500.0, OffsetDateTime.parse("2001-12-03T10:15:30+03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
        accountService.transaction(acc.id!!, withdraw(5000.0))
        val accList = accountService.myAccounts

        val txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals(3500.0.toBigDecimal(), txHistory[0].newValue)
        assertEquals(4300.0.toBigDecimal(), txHistory[1].newValue)
        assertEquals(1500.0.toBigDecimal(), txHistory[2].newValue)
        assertEquals(4500.0.toBigDecimal(), txHistory[3].newValue)
        assertEquals(5500.0.toBigDecimal(), txHistory[4].newValue)
        assertEquals(500.0.toBigDecimal(), txHistory[5].newValue)

        assertEquals(BigDecimal.valueOf(500.0), accList[0].currentValue)
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    open fun `update deposit transaction`() {
        currencyService!!.create(currency("RUB"))
        val dto = AccountNewDTO().also { it.currency = currId("RUB") }
        val deposit1 = deposit(400.0)
        val deposit2 = deposit(600.0)
        val deposit3 = deposit(800.0)
        val withdraw1 = withdraw(300.0)
        val withdraw2 = withdraw(500.0)
        val withdraw3 = withdraw(700.0)

        val acc = accountService!!.create(dto)
        deposit1.id = accountService.transaction(acc.id!!, deposit1).id
        accountService.transaction(acc.id!!, withdraw1).id
        accountService.transaction(acc.id!!, withdraw2)
        deposit2.id = accountService.transaction(acc.id!!, deposit2).id
        accountService.transaction(acc.id!!, withdraw3).id
        accountService.transaction(acc.id!!, deposit3)
        val accList = accountService.myAccounts

        assertEquals(BigDecimal.valueOf(300.0), accList[0].currentValue)

        var txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals(400.0.toBigDecimal(), txHistory[0].newValue)
        assertEquals(100.0.toBigDecimal(), txHistory[1].newValue)
        assertEquals((-400.0).toBigDecimal(), txHistory[2].newValue)
        assertEquals(200.0.toBigDecimal(), txHistory[3].newValue)
        assertEquals((-500.0).toBigDecimal(), txHistory[4].newValue)
        assertEquals(300.0.toBigDecimal(), txHistory[5].newValue)

        deposit1.amount = 250.0.toBigDecimal()
        deposit2.amount = 450.0.toBigDecimal()
        accountService.transaction(acc.id!!, deposit1)
        accountService.transaction(acc.id!!, deposit2)

        txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
                .iterator().asSequence().toList()

        assertEquals(6, txHistory.size)
        assertEquals(250.0.toBigDecimal(), txHistory[0].newValue)
        assertEquals((-50.0).toBigDecimal(), txHistory[1].newValue)
        assertEquals((-550.0).toBigDecimal(), txHistory[2].newValue)
        assertEquals((-100.0).toBigDecimal(), txHistory[3].newValue)
        assertEquals((-800.0).toBigDecimal(), txHistory[4].newValue)
        assertEquals(0.0.toBigDecimal(), txHistory[5].newValue)

        assertEquals(accountService.myAccounts[0].currentValue, BigDecimal.valueOf(0.0))
    }

    private fun withdraw(`val`: Double) = WithdrawalTxDTO().also { dto ->
        dto.amount = BigDecimal.valueOf(`val`)
        dto.currency = currId("RUB")
        dto.reason = "reason"
    }

    private fun insertWithdraw(`val`: Double, dateTime: OffsetDateTime) = WithdrawalTxDTO().also { dto ->
        dto.amount = BigDecimal.valueOf(`val`)
        dto.createDate = dateTime
        dto.currency = currId("RUB")
        dto.reason = "reason"
    }

    private fun deposit(`val`: Double) = DepositTxDTO().also { dto ->
        dto.currency = currId("RUB")
        dto.amount = BigDecimal.valueOf(`val`)
        dto.reason = "reason"
    }

    private fun insertDeposit(`val`: Double, dateTime: OffsetDateTime) = DepositTxDTO().also { dto ->
        dto.currency = currId("RUB")
        dto.createDate = dateTime
        dto.amount = BigDecimal.valueOf(`val`)
        dto.reason = "reason"
    }

    private fun currId(name: String) = CurrencyIdDTO().also { it.code = name }

    private fun currency(name: String) = CurrencyDTO().also { curr ->
        curr.code = name
        curr.humanReadableName = "Test name: $name"
    }
}
