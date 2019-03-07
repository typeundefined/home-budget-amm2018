package dsr.amm.homebudget;

import dsr.amm.homebudget.data.dto.*;
import dsr.amm.homebudget.data.entity.Category;
import dsr.amm.homebudget.data.entity.tx.DepositTx;
import dsr.amm.homebudget.data.entity.tx.Transaction;
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx;
import dsr.amm.homebudget.data.repository.AccountRepository;
import dsr.amm.homebudget.data.repository.CategoryRepository;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import dsr.amm.homebudget.data.repository.TransactionRepository;
import dsr.amm.homebudget.data.spec.TxSpecs;
import dsr.amm.homebudget.service.AccountService;
import dsr.amm.homebudget.service.AuthService;
import dsr.amm.homebudget.service.CurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.time.OffsetDateTime.parse;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HomeBudgetApplication.class, TestConfig.class})
@ActiveProfiles("test")
public class TransactionSpecTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRepository currRepo;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TransactionRepository<WithdrawalTx> withdrawalTxRepo;

    @Autowired
    private TransactionRepository<DepositTx> depositTxRepo;

    @Autowired
    private TransactionRepository<Transaction> txRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Before
    @Transactional
    @Commit
    public void before() {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setUsername("jpetrucci");
        dto.setPassword("");
        dto.setFullName("");

        authService.register(dto);

        String username = authService.getMyself().getUsername();
        UserDetails realUser = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(realUser, realUser.getPassword(), realUser.getAuthorities()));
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    public void createdAfterWorks() {
        currencyService.create(currency("RUB"));


        AccountNewDTO dto = new AccountNewDTO();
        dto.setCurrency(currId("RUB"));
        AccountDTO acc = accountService.create(dto);

        createDeposit(acc, 20d, "2018-12-03T10:15:30+03:00");
        createDeposit(acc, 14d, "2018-12-04T01:00:30+03:00");
        createDeposit(acc, 12d, "2018-12-04T05:00:30+03:00");

        List<Transaction> result = txRepo.findAll(TxSpecs.createdAfter(parse("2018-12-04T01:01:30+03:00")), Sort.by("createDate"));
        List<Transaction> result2 = txRepo.findAll(TxSpecs.createdAfter(parse("2018-12-03T11:01:30+03:00")), Sort.by("createDate"));

        assertEquals(1, result.size());
        assertEquals(valueOf(12d), ((DepositTx) result.get(0)).getAmount());

        assertEquals(2, result2.size());
        assertEquals(valueOf(14d), ((DepositTx) result2.get(0)).getAmount());
        assertEquals(valueOf(12d), ((DepositTx) result2.get(1)).getAmount());
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    public void hasCategoryWorks() {
        currencyService.create(currency("RUB"));

        AccountNewDTO dto = new AccountNewDTO();
        dto.setCurrency(currId("RUB"));
        AccountDTO acc = accountService.create(dto);

        createDeposit(acc, 20d, "2018-12-03T10:15:30+03:00");
        DepositTx d2 = createDeposit(acc, 14d, "2018-12-04T01:00:30+03:00");
        createDeposit(acc, 12d, "2018-12-04T05:00:30+03:00");

        Category cat = createCategory("test-category");

        d2.setCategory(cat);
        depositTxRepo.save(d2);

        List<Transaction> result = txRepo.findAll(TxSpecs.hasCategory(singletonList("test-category")), Sort.by("createDate"));

        assertEquals(1, result.size());
        assertEquals(valueOf(14d), ((DepositTx) result.get(0)).getAmount());
    }

    private Category createCategory(String name) {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription("desc");
        cat.setOwner(authService.getMyself());
        return categoryRepo.save(cat);
    }

    private DepositTx createDeposit(AccountDTO acc, double v, String date) {
        DepositTx tx = new DepositTx();
        tx.setAmount(valueOf(v));
        tx.setCurrency(currRepo.findById("RUB").get());
        tx.setCreateDate(parse(date));
        tx.setReason("reason");
        tx.setSrc(accountRepo.findById(acc.getId()).get());
        tx = depositTxRepo.save(tx);
        return tx;
    }


    private CurrencyIdDTO currId(String name) {
        CurrencyIdDTO dto = new CurrencyIdDTO();
        dto.setCode(name);
        return dto;
    }

    private CurrencyDTO currency(String name) {
        CurrencyDTO curr = new CurrencyDTO();
        curr.setCode(name);
        curr.setHumanReadableName("Test name: " + name);
        return curr;
    }
}
