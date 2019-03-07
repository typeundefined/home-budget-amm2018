package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.controller.exception.ApiException;
import dsr.amm.homebudget.controller.exception.ForbiddenException;
import dsr.amm.homebudget.controller.exception.NotFoundException;
import dsr.amm.homebudget.data.dto.*;
import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.Currency;
import dsr.amm.homebudget.data.entity.tx.DepositTx;
import dsr.amm.homebudget.data.entity.tx.Transaction;
import dsr.amm.homebudget.data.entity.tx.WithdrawalTx;
import dsr.amm.homebudget.data.repository.AccountRepository;
import dsr.amm.homebudget.data.repository.CurrencyRepository;
import dsr.amm.homebudget.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private TransactionRepository<Transaction> transactionRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public List<AccountDTO> getMyAccounts() {
        List<Account> accList = repository.findByOwner(authService.getMyself());
        return mapper.mapAsList(accList, AccountDTO.class);
    }

    @Transactional(readOnly = true)
    public AccountDTO getAccountById(Long id) {
        Account account = getAccount(id, false);
        return mapper.map(account, AccountDTO.class);
    }

    @Transactional
    public AccountDTO create(AccountNewDTO newAcc) {
        Account account = mapper.map(newAcc, Account.class);
        account.setCurrentValue(BigDecimal.ZERO);
        account.setCreateDate(OffsetDateTime.now());
        account.setOwner(authService.getMyself());


        // TODO: move to the mapper!
        account.setCurrency(getCurrency(newAcc.getCurrency()));

        Account res = repository.save(account);
        return mapper.map(res, AccountDTO.class);
    }

    @Transactional
    public TransactionDTO deposit(Long accountId, DepositTxDTO tx) {
        Account account = getAccount(accountId);
        DepositTx transaction = mapper.map(tx, DepositTx.class);
        transaction.setCreateDate(OffsetDateTime.now());
        transaction.setSrc(account);

        BigDecimal value = account.getCurrentValue();
        BigDecimal amount = convertToCurrency(
                transaction.getAmount(),
                transaction.getCurrency(),
                account.getCurrency());

        BigDecimal newValue = value.add(amount);
        account.setCurrentValue(newValue);
        transaction.setNewValue(newValue);

        DepositTx txResult = transactionRepository.save(transaction);
        repository.save(account);

        return mapper.map(txResult, TransactionDTO.class);
    }

    private Currency getCurrency(CurrencyIdDTO currency) {
        return currencyRepository.findById(currency.getCode())
                .orElseThrow(notFound("No such currency found"));
    }

    @Transactional
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

        WithdrawalTx txResult = transactionRepository.save(transaction);
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
        Long ownerId = acc.getOwner().getId();
        if (!ownerId.equals(authService.getMyself().getId())) {
            throw new ForbiddenException("You have no permission to access this account");
        }
    }

    private Supplier<ApiException> notFound(String s) {
        return () -> new NotFoundException(s);
    }

    public Page<TransactionDTO> getAccountTransactions(Pageable pageable, Long accountId) {
        Account account = getAccount(accountId, false);
        Page<Transaction> transactions = transactionRepository.findAllByAccount(pageable, account);
        return transactions.map((Transaction t) -> mapper.map(t, TransactionDTO.class));
    }

    @Transactional
    public void deleteTransaction(Long accountId, Long transactionId) {
        Account account = getAccount(accountId, false);
        // FIXME change deletion logic
        transactionRepository.deleteById(account, transactionId);
    }

}
