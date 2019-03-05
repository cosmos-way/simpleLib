package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.ItemOrder;
import com.itmoshop.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.NoResultException;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AdminService admServ;

    @Autowired
    private ShaPasswordEncoder passwdEncder;

    @RequestMapping(method = RequestMethod.GET)
    public String showAccountInfo(Model model, Principal principal) {
        String email = principal.getName();
        Account account = admServ.findAccountByEmail(email);
        Set<ItemOrder> ordersSet = account.getOrdersSet();
        model.addAttribute("ordersSet", ordersSet);
        model.addAttribute("existingAccount", account);
        return "account";
    }

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String changeAccountDetails(
            @ModelAttribute("changedAccount") Account changedAccount,
            Model model,
            Principal principal)
    {
        if (isChangedEmailOccupied(changedAccount, principal)) {
            returnError(changedAccount, model);
        } else {
            updateAccount(changedAccount, model, principal);
        }
        return "account";
    }

    private void updateAccount(Account changedAccount, Model model, Principal principal) {
        Account existingAccount = null;
        try {
            existingAccount = admServ.findAccountByEmail(principal.getName());
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        String existingPw = existingAccount.getPassword();

        if (!changedAccount.getPassword().equals(existingPw)) {
            String newEncodedPw = passwdEncder.encodePassword(changedAccount.getPassword(), null);
            changedAccount.setPassword(newEncodedPw);
        }

        changedAccount.setId(existingAccount.getId());
        changedAccount.setOrdersSet(existingAccount.getOrdersSet());
        changedAccount.setRole(existingAccount.getRole());
        admServ.saveAccount(changedAccount);

        model.addAttribute("existingAccount", changedAccount);
    }

    private void returnError(Account changedAccount, Model model) {
        changedAccount.setPassword("");
        model.addAttribute("error", new Object());
        model.addAttribute("existingAccount", changedAccount);
    }

    private boolean isChangedEmailOccupied(Account changedAccount, Principal principal) {
        String changedEmail = changedAccount.getEmail();
        String existingEmail = principal.getName();
        if (changedEmail.equals(existingEmail))
            return false;
        Account accountByEmail = null;
        try {
            accountByEmail = admServ.findAccountByEmail(changedEmail);
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

}
