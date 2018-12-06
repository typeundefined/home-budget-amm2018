package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.tx.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by knekrasov on 10/16/2018.
 */
@Repository
public interface TransactionRepository<T extends Transaction> extends PagingAndSortingRepository<T, Long> {
    @Query("from #{#entityName} tx where src = ?1 order by tx.createDate")
    Page<T> findAllByAccount(Pageable pageable, Account account);
}
