package dsr.amm.homebudget.data.repository

import dsr.amm.homebudget.data.entity.Account
import dsr.amm.homebudget.data.entity.tx.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import java.time.OffsetDateTime

/**
 * Created by knekrasov on 10/16/2018.
 */
@Repository
interface TransactionRepository<T : Transaction> : PagingAndSortingRepository<T, Long> {
    @Query("from #{#entityName} tx where src = ?1 order by tx.createDate")
    fun findAllByAccount(pageable: Pageable, account: Account): Page<T>

    @Query("from #{#entityName} tx where src = ?1 and id = ?2")
    fun findByAccountAndId(account: Account, id: Long): Account?

    @Query("SELECT * from TRANSACTION where src_id = ?1 order by create_Date limit 1", nativeQuery = true)
    fun findLastByAccount(account: Account): T?

    @Query("from #{#entityName} tx where src = ?1 and tx.createDate between ?2 and ?3 order by tx.createDate")
    fun findAllByAccountWithTimeFilter(pageable: Pageable, account: Account, from: OffsetDateTime, to: OffsetDateTime): Page<T>

    @Query("from #{#entityName} tx where src = ?1 and tx.createDate >= ?2 order by tx.createDate")
    fun findAllByAccountWithTimeFilterFrom(pageable: Pageable, account: Account, from: OffsetDateTime): Page<T>

    @Query("from #{#entityName} tx where src = ?1 and tx.createDate <= ?2 order by tx.createDate")
    fun findAllByAccountWithTimeFilterTo(pageable: Pageable, account: Account, to: OffsetDateTime): Page<T>

    @Query("SELECT * from TRANSACTION where src_id = ?1 and create_Date < ?2 order by create_Date desc limit 1", nativeQuery = true)
    fun findLastEarlierThan(account: Account, earlierThan: OffsetDateTime): T?

    @Query("SELECT * from TRANSACTION where src_id = ?1 and create_Date > ?2 order by create_Date", nativeQuery = true)
    fun findAllLaterThan(pageable: Pageable, account: Account, from: OffsetDateTime): Page<T>

    @Modifying
    @Query("delete from #{#entityName} tx where src = :account and tx.id = :id")
    fun deleteById(@Param("account") account: Account, @Param("id") transactionId: Long)
}
