package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public void login(LoginDTO loginDTO) {
       authService.login(loginDTO);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void registration(@RequestBody RegistrationDTO registrationDTO){
        authService.userRegistration(registrationDTO);
    }

}
