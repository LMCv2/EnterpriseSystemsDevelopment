package com.aib.websystem.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    @Autowired
    private AccountRepository accountRepository;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account account = accountRepository.findById(username).orElse(null);
        if (account.getPassword().equals(password)) {
            request.setAttribute("result", account.getUsername());
            request.getSession().setAttribute("user", account);
            response.sendRedirect("/dashboard");
        } else {
            request.setAttribute("result", "Invalid username or password");
            response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
