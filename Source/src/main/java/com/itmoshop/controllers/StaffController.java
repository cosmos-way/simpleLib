package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.Book;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private AdminService admServ;
    @Autowired
    private DataService dataServ;

    @RequestMapping(method = RequestMethod.GET)
    public String staff(
            Model model) {
        try {
            model.addAttribute("webRootPathFolders", Arrays.asList(admServ.getWebRootPath().listFiles()));
        } catch (NullPointerException e) {
            model.addAttribute("webRootPathFolders", admServ.getWebRootPath().toString());
        }
        return "staff";
    }



}
