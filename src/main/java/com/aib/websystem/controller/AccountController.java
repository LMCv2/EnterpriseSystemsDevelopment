package com.aib.websystem.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.aib.websystem.entity.Account;

@WebServlet(name = "account", urlPatterns = { "/account" })
public class AccountController extends HttpServlet {
    @Autowired
    private AccountRepository accountRepository;
    RequestDispatcher rd;

    public void init() {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("id".equalsIgnoreCase(action)) {

        } else if ("create-account".equalsIgnoreCase(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (accountRepository.existsById(username)) {
                // return null;
            }
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            accountRepository.save(account);
            request.setAttribute("account", account);
            rd = getServletContext().getRequestDispatcher("/greeting.jsp");
            rd.forward(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Account account = accountRepository.findById(username).orElse(null);
            if (account.getPassword().equals(password)) {
                request.setAttribute("result", account.getUsername());
                rd = getServletContext().getRequestDispatcher("/dashboard.jsp");
            } else {
                request.setAttribute("result", "Invalid username or password");
                rd = getServletContext().getRequestDispatcher("/index.jsp");
            }
            rd.forward(request, response);
        }
        else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}