package dsr.amm.homebudget.data.repository;

import dsr.amm.homebudget.data.entity.Account;
import dsr.amm.homebudget.data.entity.tx.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by knekrasov on 10/16/2018.
 */
@Repository
public interface TransactionRepository<T extends Transaction> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
    @Query("from #{#entityName} tx where src = ?1 order by tx.createDate")
    Page<T> findAllByAccount(Pageable pageable, Account account);

    @Modifying
    @Query("delete from #{#entityName} tx where src = :account and tx.id = :id")
    void deleteById(@Param("account") Account account, @Param("id") Long transactionId);
}
