package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegisterDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        authenticationManager.authenticate(token);
        // TODO: session registration
    }

    public void register(RegisterDTO registerDTO) {
        Person person = mapper.map(registerDTO, Person.class);

        repository.findByUsername(person.getUsername()).ifPresent(
                existingPerson -> {
                    throw new RuntimeException("This username is already in use");
                }
        );
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRegisterDate(OffsetDateTime.now());
        repository.save(person);
    }
}
