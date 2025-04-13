package com.aib.websystem.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aib.websystem.WebsystemApplication;
import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventType;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.repository.AccountRepository;
import com.aib.websystem.repository.FruitRepository;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.EventRepository;
import com.aib.websystem.service.StockService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "" })
public class CurrentAccountController extends HttpServlet {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StockService stockService;

    private static final Logger logger = LoggerFactory.getLogger(WebsystemApplication.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd;
        if (accountRepository.count() == 0) {
            rd = req.getRequestDispatcher("/sys-init.jsp");
        } else if (req.getParameter("signinkey") != null) {
            rd = req.getRequestDispatcher("/sign-up.jsp");
        } else {
            rd = req.getRequestDispatcher("/sign-in.jsp");
        }
        rd.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("sysinit")) {
            this.sysinit(req, res);
        } else if (action.equals("signup")) {
            this.signup(req, res);
        } else if (action.equals("signin")) {
            this.signin(req, res);
        } else if (action.equals("signout")) {
            this.signout(req, res);
        } else {
            this.doGet(req, res);
        }
    }

    public void sysinit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("init database");

        // add admin
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        accountRepository.save(new Account(username, password, Role.ADMIN));

        // add fruit
        for (String name : new String[] { "Apple", "Banana", "Orange", "Strawberry", "Mango" }) {
            Fruit fruit = new Fruit(name);
            fruitRepository.save(fruit);
            stockService.addFruitToAllLocation(fruit);
        }

        // add location
        for (Object[] item : new Object[][] {
                { "Source Warehouse 1", "Hong Kong", LocationType.SOURCE_WAREHOUSE },
                { "Central Warehouse 1", "Hong Kong", LocationType.CENTRAL_WAREHOUSE },
                { "Central Warehouse 2", "Hong Kong", LocationType.CENTRAL_WAREHOUSE },
                { "Central Warehouse 3", "London", LocationType.CENTRAL_WAREHOUSE },
                { "Bakery Shop 1", "Hong Kong", LocationType.SHOP }
        }) {
            Location location = new Location((String) item[0], (String) item[1], (LocationType) item[2]);
            locationRepository.save(location);
            stockService.addAllFruitToLocation(location);
        }

        // borrowing
        Fruit fruit = fruitRepository.findAll().iterator().next();

        // Create a specification that returns all locations
        Iterator<Location> it = locationRepository.findAll().iterator();
        eventRepository.save(new Event(fruit, 100, EventType.BORROWING, it.next(), it.next(), new Date(), new Date(), EventStatus.WAITAPPROVE));

        // account
        accountRepository.save(new Account("shop", "a", Role.SHOP_STAFF, it.next()));

        res.sendRedirect("/");
    }

    public void signup(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    public void signin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<Account> account = accountRepository.findById(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("current_account", account.get());
            switch (account.get().getRole()) {
                case ADMIN:
                    session.setAttribute("permissions", Set.of("dashboard", "stock", "event", "fruit", "location", "account"));
                    res.sendRedirect("/dashboard/");
                    break;
                case SHOP_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard", "stock", "event"));
                    res.sendRedirect("/dashboard/");
                    break;
                case CENTRAL_WAREHOUSE_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard", "stock", "event"));
                    res.sendRedirect("/dashboard/");
                    break;
                case SOURCE_WAREHOUSE_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard", "stock", "event"));
                    res.sendRedirect("/dashboard/");
                    break;
                case SENIOR_MANAGEMENT:
                    session.setAttribute("permissions", Set.of("dashboard", "fruit", "location", "account"));
                    res.sendRedirect("/dashboard/");
                    break;
            }
        } else {
            req.setAttribute("result", "Invalid username or password");
            res.sendRedirect("/");
        }
    }

    public void signout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        res.sendRedirect("/");
    }
}
