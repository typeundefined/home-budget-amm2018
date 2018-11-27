package dsr.amm.homebudget.controller;


import dsr.amm.homebudget.data.dto.AccountDTO;
import dsr.amm.homebudget.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by knekrasov on 10/15/2018.
 */

// Account controller
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    // Get account
    @RequestMapping(method = RequestMethod.GET)
    public List<AccountDTO> getAccount() {
        return service.getAccounts();
    }

    // Post account
    @RequestMapping(method = RequestMethod.POST)
    public void createCurrency(@RequestBody @Valid AccountDTO acc) {
        service.create(acc);
    }

    // Delete account
    @GetMapping("/account/{id}")
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCurrency(@PathVariable long id) {
        service.delete(id);
    }
}
