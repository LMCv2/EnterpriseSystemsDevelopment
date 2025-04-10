package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aib.websystem.repository.AccountRepository;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public String getFruits(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        return "/pages/user/index";
    }
}
