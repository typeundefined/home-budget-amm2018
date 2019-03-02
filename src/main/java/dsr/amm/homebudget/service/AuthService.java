package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.controller.exception.UniqueViolationException;
import dsr.amm.homebudget.data.dto.JwtResponseDTO;
import dsr.amm.homebudget.data.dto.LoginDTO;
import dsr.amm.homebudget.data.dto.RegistrationDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrikaMapper mapper;

    @Autowired
    private PersonRepository repository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    @Transactional(readOnly = true)
    public JwtResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JwtResponseDTO(jwt);
    }

    @Transactional
    public void register(RegistrationDTO registrationDTO) {
        Person person = mapper.map(registrationDTO, Person.class);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRegisterDate(OffsetDateTime.now());
        try {
            this.repository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueViolationException("Username " + person.getUsername() + " already exists");
        }
    }

    public Person getMyself() {
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getPerson();
    }
}

