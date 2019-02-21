package dsr.amm.homebudget.service;

import dsr.amm.homebudget.data.entity.Person;
import dsr.amm.homebudget.data.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = repository.findByUsername(username).
                orElseThrow(
                () -> new RuntimeException("Username not found"));
        return new Principal(person);
    }

}
