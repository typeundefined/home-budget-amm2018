package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.JwtResponseDTO;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private AuthService authService;
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(path = "/login", method = POST)
    public JwtResponseDTO login(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @RequestMapping(path = "/register", method = POST)
    public void registration(@RequestBody @Valid RegistrationDTO registrationDTO) {
        authService.register(registrationDTO);
    }
}
