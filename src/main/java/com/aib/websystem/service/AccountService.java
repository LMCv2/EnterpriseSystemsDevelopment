package com.aib.websystem.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;
import java.lang.Long;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public boolean login(String username, String password) {
        //Account account = accountRepository.findById((username));
        
        return false;
    }
}