package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.controller.exception.ApiException;
import dsr.amm.homebudget.data.dto.*;
import dsr.amm.homebudget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by knekrasov on 10/15/2018.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = GET)
    public List<AccountDTO> getCurrent() {
        return accountService.getMyAccounts();
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public AccountDTO createAccount(@Valid @RequestBody AccountNewDTO newAcc) {
        return accountService.create(newAcc);
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

}
