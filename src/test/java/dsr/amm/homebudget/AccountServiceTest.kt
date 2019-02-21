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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.annotation.Commit
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

import java.math.BigDecimal
import java.util.ArrayList
import java.util.Optional

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

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
        accountService.deposit(acc.id!!, deposit(14.0))
        val accList = accountService.myAccounts

        assertEquals(accList[0].currentValue, BigDecimal.valueOf(14.0))

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
        accountService.deposit(acc.id!!, deposit(20.0))
        accountService.withdraw(acc.id!!, withdraw(11.0))
        val accList = accountService.myAccounts

        assertEquals(accList[0].currentValue, BigDecimal.valueOf(9.0))

        val txHistory = accountService.getAccountTransactions(Pageable.unpaged(), acc.id!!)
        assertEquals(txHistory.totalElements, 2L)
        val tx = txHistory
                .stream()
                .filter { t -> t.type == "withdrawal" }
                .findFirst().get()

        assertEquals("withdrawal", tx.type)
        assertTrue(tx is WithdrawalTxDTO)
    }

    private fun withdraw(v: Double) = WithdrawalTxDTO().also { dto ->
        dto.amount = BigDecimal.valueOf(v)
        dto.currency = currId("RUB")
        dto.reason = "reason"
    }

    private fun deposit(`val`: Double) = DepositTxDTO().also { dto ->
        dto.currency = currId("RUB")
        dto.amount = BigDecimal.valueOf(`val`)
        dto.reason = "reason"
    }

    private fun currId(name: String) = CurrencyIdDTO().also { it.code = name }

    private fun currency(name: String) = CurrencyDTO().also { curr ->
        curr.code = name
        curr.humanReadableName = "Test name: $name"
    }
}
