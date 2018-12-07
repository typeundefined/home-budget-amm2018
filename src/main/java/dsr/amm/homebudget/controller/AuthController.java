package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.JwtResponseDTO;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path="/login", method = POST)
    public JwtResponseDTO login(@RequestBody LoginDTO loginDTO) {
       return authService.login(loginDTO);
    }

    @RequestMapping(path = "/register", method = POST)
    public void registration(@RequestBody RegistrationDTO registrationDTO){
        authService.register(registrationDTO);
    }

}
