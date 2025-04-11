package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Role;
import com.aib.websystem.repository.AccountRepository;

@Controller
@RequestMapping(path = "/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/account/index";
    }

    @PostMapping("")
    public String createAccount(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role) {
        Account account = new Account(username, password, role);
        accountRepository.save(account);
        return "/pages/account/index";
    }

    @GetMapping("/{username}")
    public String getAccount(@PathVariable String username, Model model) {
        model.addAttribute("account", accountRepository.findById(username).orElse(null));
        return "/pages/account/edit";
    }

    @PutMapping("/{username}")
    public String updateAccount(
            @PathVariable String username,
            @RequestParam String password) {
        if (accountRepository.existsById(username)) {
            Account account = accountRepository.findById(username).get();
            account.setPassword(password);
            accountRepository.save(account);
        }
        return "/pages/account/index";
    }

    @DeleteMapping("/{username}")
    public String deleteAccount(@PathVariable String username) {
        if (accountRepository.existsById(username)) {
            accountRepository.deleteById(username);
        }
        return "/pages/account/index";
    }
}
