package com.itmoshop.controllers;

import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @Autowired
    private DataService dataServ;

    @RequestMapping("/products")
    public String products(){
        return "products";
    }

    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }

    @RequestMapping("/typography")
    public String typography(){
        return "typography";
    }
}
