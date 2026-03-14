package com.devsu.hackerearth.backend.account.service;
import java.util.List;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
public interface AccountService {
    List<AccountDto> getAll();
    AccountDto getById(Long id);
    AccountDto create(AccountDto dto);
    AccountDto update(AccountDto dto);
    AccountDto partialUpdate(Long id, PartialAccountDto dto);
    void deleteById(Long id);
}
