package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.AccountDTO;
import dsr.amm.homebudget.data.dto.AccountNewDTO;
import dsr.amm.homebudget.data.dto.CurrencyIdDTO;
import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.AccountRepository;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public List<AccountDTO> getMyAccounts() {
        // FIXME return my accounts only!
        Iterable<Account> accList = repository.findAll();
        return mapper.mapAsList(accList, AccountDTO.class);
    }

    @Transactional
    public AccountDTO create(AccountNewDTO newAcc) {
        Account account = mapper.map(newAcc, Account.class);
        account.setOwner(getMyself());
        account.setCurrentValue(BigDecimal.ZERO);
        account.setCreateDate(OffsetDateTime.now());

        // TODO: move to the mapper!
        account.setCurrency(getCurrency(newAcc.getCurrency()));

        Account res = repository.save(account);
        return mapper.map(res, AccountDTO.class);
    }

    private Currency getCurrency(CurrencyIdDTO currency) {
        // FIXME: this must not return HTTP 500
        return currencyRepository.findById(currency.getCode()).orElseThrow(() -> new RuntimeException("No such currency found"));
    }

    private Person getMyself() {
        // TODO implement me when authentication gets ready
        return null;
    }
}
