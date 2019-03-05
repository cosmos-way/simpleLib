package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.AccountRole;
import com.itmoshop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.NoResultException;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AdminService admServ;

    @Autowired
    private ShaPasswordEncoder passwdEncder;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String registerAccount(@ModelAttribute("existingAccount") Account newAccount, Model model) {
        Account existingAccount = null;
        try {
            existingAccount = admServ.findAccountByEmail(newAccount.getEmail());
        } catch (NoResultException exc) {
            String rawPassword = newAccount.getPassword();
            newAccount.setPassword(passwdEncder.encodePassword(rawPassword, null));
            newAccount.setRole(AccountRole.USER);
            admServ.saveAccount(newAccount);
            return "login";
        }
        model.addAttribute("error", new Object());
        model.addAttribute("newAccount", newAccount);
        return "register";
    }
}
