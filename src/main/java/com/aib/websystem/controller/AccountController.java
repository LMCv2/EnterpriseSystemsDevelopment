package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;


@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/create-account")
    public Account createAccount(@RequestParam String username,
            @RequestParam String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountRepository.save(account);
    }
}
