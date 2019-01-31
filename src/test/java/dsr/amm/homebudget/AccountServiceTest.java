package dsr.amm.homebudget;

import dsr.amm.homebudget.data.dto.AccountNewDTO;
import dsr.amm.homebudget.data.dto.CurrencyDTO;
import dsr.amm.homebudget.data.dto.CurrencyIdDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.repository.AccountRepository;
import dsr.amm.homebudget.service.AccountService;
import dsr.amm.homebudget.service.AuthService;
import dsr.amm.homebudget.service.CurrencyService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AuthService authService;

    @Before
    public void before() {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setUsername("jpetrucci");
        dto.setPassword("");
        dto.setFullName("");

        authService.register(dto);
    }

    @Test
    @Transactional
    @WithUserDetails("jpetrucci")
    public void canCreateAccount() {
        currencyService.create(currency("RUB"));

        AccountNewDTO dto = new AccountNewDTO();
        dto.setCurrency(currId("RUB"));
        accountService.create(dto);

        List<Account> accList = new ArrayList<>();
        repository.findAll().forEach(accList::add);

        assertFalse(accList.isEmpty());
    }

    private CurrencyIdDTO currId(String rub) {
        CurrencyIdDTO dto = new CurrencyIdDTO();
        dto.setCode(rub);
        return dto;
    }

    private CurrencyDTO currency(String name) {
        CurrencyDTO curr = new CurrencyDTO();
        curr.setCode(name);
        curr.setHumanReadableName("Test name: " + name);
        return curr;
    }
}
