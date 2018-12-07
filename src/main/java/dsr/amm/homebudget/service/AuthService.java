package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.UniqueConditionException;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.OffsetDateTime;


@Service
public class AuthService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrikaMapper mapper;

    @Autowired
    private PersonRepository repository;

    @Transactional
    public void login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }
        catch(AuthenticationException e){
            System.err.println("Failed to authenticate : " + loginDTO.getUsername());
        }
    }

    public void userRegistration(RegistrationDTO registrationDTO) throws UniqueConditionException {
        Person person = mapper.map(registrationDTO, Person.class);

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRegisterDate(OffsetDateTime.now());
        try {
            this.repository.save(person);
        }
        catch (DataIntegrityViolationException e) {
            throw new UniqueConditionException("Username " + person.getUsername() + "already exists");
        }
    }



}

