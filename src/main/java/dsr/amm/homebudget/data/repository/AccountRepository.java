package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Account repository
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}