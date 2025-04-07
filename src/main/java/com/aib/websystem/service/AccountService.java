package com.aib.websystem.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}