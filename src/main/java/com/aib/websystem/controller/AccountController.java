package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;


@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/create-account")
    public Account createAccount(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);
        return accountRepository.save(account);
    }

    @PostMapping("/update-account")
    public Account updateAccount(@RequestParam String username,
            @RequestParam String password) {
        Account account = accountRepository.findById(username).orElse(null);
        account.setPassword(password);
        return accountRepository.save(account);
    }
    @PostMapping("/delete-account")
    public boolean deleteAccount(@RequestParam String username) {
        if(accountRepository.existsById(username)) {
            accountRepository.deleteById(username);
            return true;
        } else {
            return false;
        }
    }
    @PostMapping("/get-all-account")
    public Iterable<Account> getAllAccount() {
        return accountRepository.findAll();
    }
    @PostMapping("/get-account")
    public Account getAccount(@RequestParam String username) {
        return accountRepository.findById(username).orElse(null);
    }
    @PostMapping("/logout")
    public void logout() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        session.invalidate();
        try {
            attr.getResponse().sendRedirect("/sign-in.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
