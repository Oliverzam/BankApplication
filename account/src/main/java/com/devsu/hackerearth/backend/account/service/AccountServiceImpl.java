package com.devsu.hackerearth.backend.account.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.devsu.hackerearth.backend.account.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    private AccountDto toDto(Account a) {
        return new AccountDto(a.getId(), a.getNumber(), a.getType(), a.getInitialAmount(), a.isActive(), a.getClientId());
    }
    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
    @Override
    public AccountDto getById(Long id) {
        return toDto(accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found")));
    }
    @Override
    public AccountDto create(AccountDto dto) {
        Account a = new Account();
        a.setNumber(dto.getNumber());
        a.setType(dto.getType());
        a.setInitialAmount(dto.getInitialAmount());
        a.setActive(dto.isActive());
        a.setClientId(dto.getClientId());
        return toDto(accountRepository.save(a));
    }
    @Override
    public AccountDto update(AccountDto dto) {
        if (dto.getId() == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        Account account = accountRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setNumber(dto.getNumber());
        account.setType(dto.getType());
        account.setInitialAmount(dto.getInitialAmount());
        account.setActive(dto.isActive());
        account.setClientId(dto.getClientId());
        return toDto(accountRepository.save(account));
    }
    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setActive(dto.isActive());
        return toDto(accountRepository.save(account));
    }
    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        accountRepository.delete(account);
    }
}
