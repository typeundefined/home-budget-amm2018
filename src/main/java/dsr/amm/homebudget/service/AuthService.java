package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegisterDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import dsr.amm.homebudget.error.UniqueConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.OffsetDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrikaMapper mapper;

    @Transactional
    public void login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
    }

    public void register(RegisterDTO registerDTO) throws UniqueConditionException {
        Person person = mapper.map(registerDTO, Person.class);

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRegisterDate(OffsetDateTime.now());
        try {
            this.save(person);
        }
        catch (DataIntegrityViolationException e) {
            throw new UniqueConditionException(
                    "Username '" + person.getUsername() + "' is already in use",
                    HttpServletResponse.SC_NOT_ACCEPTABLE
            );
        }
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }
}
