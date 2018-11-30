package dsr.amm.homebudget.service;

import dsr.amm.homebudget.data.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    public void login(LoginDTO loginDTO) {
        // FIXME: implement authentication logic
        // authenticationManager.authenticate()
        // etc
        throw new RuntimeException("Not implemented");
    }
}
