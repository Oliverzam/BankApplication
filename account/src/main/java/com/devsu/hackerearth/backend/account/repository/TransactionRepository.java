package com.devsu.hackerearth.backend.account.repository;

import com.devsu.hackerearth.backend.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    @Query("SELECT t FROM Transaction t " +
           "JOIN Account a ON t.accountId = a.id " +
           "WHERE a.clientId = :clientId " +
           "AND t.date >= :start AND t.date <= :end")
    List<Transaction> findByClientIdAndDateBetween(
        @Param("clientId") Long clientId,
        @Param("start") Date start,
        @Param("end") Date end);

    Optional<Transaction> findTopByAccountIdOrderByDateDesc(Long accountId);
}