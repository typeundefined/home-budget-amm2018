package dsr.amm.homebudget.service;

import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void login(LoginDTO loginDTO) {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
               loginDTO.getPassword()));
    }
}
