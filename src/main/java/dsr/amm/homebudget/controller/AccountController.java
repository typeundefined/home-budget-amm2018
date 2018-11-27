package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.AccountDTO;
import dsr.amm.homebudget.data.dto.AccountNewDTO;
import dsr.amm.homebudget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by knekrasov on 10/15/2018.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AccountDTO> getCurrent() {
        return accountService.getMyAccounts();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@Valid @RequestBody AccountNewDTO newAcc) {
        return accountService.create(newAcc);
    }
}
