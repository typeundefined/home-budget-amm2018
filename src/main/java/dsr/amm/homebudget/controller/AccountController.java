package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.AccountDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by knekrasov on 10/15/2018.
 */

@RestController
@RequestMapping("/account")
public class AccountController {

    @RequestMapping(method = RequestMethod.GET)
    public AccountDTO getCurrent() {
        AccountDTO dto = new AccountDTO();
        dto.setAmount(140d);
        return dto;
    }
}
