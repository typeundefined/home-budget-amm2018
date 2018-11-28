package dsr.amm.homebudget.service;

import dsr.amm.homebudget.OrikaMapper;
import dsr.amm.homebudget.data.dto.AccountDTO;
import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// Account service
@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private OrikaMapper mapper;

    // Get account method
    @Transactional
    public List<AccountDTO> getAccounts() {
        Iterable<Account> t = repository.findAll();
        return mapper.mapAsList(t, AccountDTO.class);
    }

    // Create account method
    @Transactional
    public void create(AccountDTO curr) {
        repository.save(mapper.map(curr, Account.class));
    }

    // Delete account method
    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }
}