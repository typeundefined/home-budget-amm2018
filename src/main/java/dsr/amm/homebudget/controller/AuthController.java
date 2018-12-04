package dsr.amm.homebudget.controller;

import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegisterDTO;
import dsr.amm.homebudget.error.UniqueConditionException;
import dsr.amm.homebudget.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void register(RegisterDTO registerDTO) {
        authService.register(registerDTO);
    }

    @ExceptionHandler(UniqueConditionException.class)
    public void uniqueConditionHandler(HttpServletResponse response, UniqueConditionException exception) throws IOException {
        response.sendError(exception.getResponseCode(), exception.getMessage());
    }

}
