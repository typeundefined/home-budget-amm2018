package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.Person;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

/**
 * Created by knekrasov on 10/16/2018.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("from Account a where a.id = ?1")
    @Lock(PESSIMISTIC_WRITE)
    Account findByIdForUpdate(Long id);

    List<Account> findByOwner(Person p);
}
