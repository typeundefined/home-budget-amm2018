package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.controller.exception.ApiException;
import dsr.amm.homebudget.controller.exception.NotFoundException;
import dsr.amm.homebudget.data.dto.*;
import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.entity.tx.DepositTx;
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx;
import dsr.amm.homebudget.data.repository.AccountRepository;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import dsr.amm.homebudget.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Supplier;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransactionRepository<WithdrawalTx> withdrawalTxRepository;

    @Autowired
    private TransactionRepository<DepositTx> depositRepo;

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

    @Transactional
    public TransactionDTO deposit(Long accountId, DepositTxDTO tx) {
        return null;
    }

    private Currency getCurrency(CurrencyIdDTO currency) {
        return currencyRepository.findById(currency.getCode())
                .orElseThrow(notFound("No such currency found"));
    }

    private Person getMyself() {
        // TODO implement me when authentication gets ready
        return null;
    }

    public TransactionDTO withdraw(Long accountId, WithdrawalTxDTO tx) {
        Account acc = getAccount(accountId);

        WithdrawalTx transaction = mapper.map(tx, WithdrawalTx.class);
        transaction.setCreateDate(OffsetDateTime.now());
        transaction.setSrc(acc);

        BigDecimal value = acc.getCurrentValue();

        BigDecimal amount = convertToCurrency(transaction.getAmount(), transaction.getCurrency(), acc.getCurrency());

        BigDecimal newValue = value.subtract(amount);
        acc.setCurrentValue(newValue);
        transaction.setNewValue(newValue);

        WithdrawalTx txResult = withdrawalTxRepository.save(transaction);
        repository.save(acc);

        return mapper.map(txResult, TransactionDTO.class);
    }

    private Account getAccount(Long accountId) {
        return getAccount(accountId, true);
    }

    private Account getAccount(Long accountId, boolean needLock) {
        Account acc;
        String msg = "No account found [id = " + accountId + "]";
        if (needLock) {
            acc = repository.findByIdForUpdate(accountId).orElseThrow(notFound(msg));
        } else {
            acc = repository.findById(accountId).orElseThrow(notFound(msg));
        }

        ensureMine(acc);
        return acc;
    }

    private BigDecimal convertToCurrency(BigDecimal amount, Currency from, Currency to) {
        // FIXME add support of currency conversions
        return amount;
    }

    private void ensureMine(Account acc) {
        // TODO implement me
    }

    private Supplier<ApiException> notFound(String s) {
        return () -> new NotFoundException(s);

    }


}
