package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.PersonDTO;
import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository repository;

    @Autowired
    private OrikaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = repository.findByUsername(username).
                orElseThrow(
                () -> new RuntimeException("Username not found"));
        return new Principal(person);
    }

    @Transactional
    public List<PersonDTO> getAllUsers() {
        Iterable<Person> personList = repository.findAll();
        return mapper.mapAsList(personList, PersonDTO.class);
    }
}
