package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.controller.exception.ApiException;
import dsr.amm.homebudget.data.dto.*;
import dsr.amm.homebudget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by knekrasov on 10/15/2018.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/my", method = GET)
    public List<AccountDTO> getCurrent() {
        return accountService.getMyAccounts();
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public AccountDTO createAccount(@Valid @RequestBody AccountNewDTO newAcc) {
        return accountService.create(newAcc);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public AccountDTO getAccount(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

    @RequestMapping(value = "/{id}/transactions", method = POST)
    @ResponseStatus(CREATED)
    public TransactionDTO addTransaction(@RequestBody @Valid TransactionDTO tx, @PathVariable("id") Long accountId) {
        if (tx instanceof DepositTxDTO) {
            return accountService.deposit(accountId, (DepositTxDTO) tx);
        }
        else if (tx instanceof WithdrawalTxDTO) {
            return accountService.withdraw(accountId, (WithdrawalTxDTO) tx);
        }
        throw new ApiException("Unsupported transaction type submitted");
    }

    @RequestMapping(value = "/{id}/transactions", method = GET)
    public Page<TransactionDTO> getTransactions(Pageable pageable, @PathVariable("id") Long accountId) {
        return accountService.getAccountTransactions(pageable, accountId);
    }

    @RequestMapping(value = "/{accountId}/transactions/{transactionId}", method = DELETE)
    public void deleteTransaction(
            @PathVariable("accountId") Long accountId,
            @PathVariable("transactionId") Long transactionId) {
        accountService.deleteTransaction(accountId, transactionId);
    }

}
