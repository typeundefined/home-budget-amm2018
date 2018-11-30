package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.LoginDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public void login(LoginDTO loginDTO) {
        // FIXME: call the proper service!
    }

}
