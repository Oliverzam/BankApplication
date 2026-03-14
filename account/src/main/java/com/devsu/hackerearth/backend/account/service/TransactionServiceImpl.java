package com.devsu.hackerearth.backend.account.service;

import com.devsu.hackerearth.backend.account.exception.InsufficientFundsException;
import com.devsu.hackerearth.backend.account.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                   AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    private TransactionDto toDto(Transaction t) {
        return new TransactionDto(t.getId(), t.getDate(), t.getType(),
            t.getAmount(), t.getBalance(), t.getAccountId());
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        Transaction t = transactionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
        return toDto(t);
    }

    @Override
    public TransactionDto create(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + dto.getAccountId()));

        double currentBalance = transactionRepository
            .findTopByAccountIdOrderByDateDesc(dto.getAccountId())
            .map(Transaction::getBalance)
            .orElse(account.getInitialAmount());

        double newBalance = currentBalance + dto.getAmount();
        if (newBalance < 0) throw new InsufficientFundsException();

        Transaction t = new Transaction();
        t.setDate(dto.getDate() != null ? dto.getDate() : new Date());
        t.setType(dto.getAmount() >= 0 ? "Crédito" : "Débito");
        t.setAmount(dto.getAmount());
        t.setBalance(newBalance);
        t.setAccountId(dto.getAccountId());

        return toDto(transactionRepository.save(t));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(
            Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {
        List<Transaction> transactions = transactionRepository
            .findByClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
        if (transactions == null || transactions.isEmpty()) return new ArrayList<>();
        return transactions.stream().map(t -> {
            Account account = accountRepository.findById(t.getAccountId()).orElse(new Account());
            return new BankStatementDto(
                t.getDate(),
                "client",
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                t.getType(),
                t.getAmount(),
                t.getBalance()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        return transactionRepository.findTopByAccountIdOrderByDateDesc(accountId)
            .map(this::toDto)
            .orElse(null);
    }
}
