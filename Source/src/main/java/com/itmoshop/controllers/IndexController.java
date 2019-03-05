package com.itmoshop.controllers;

import com.itmoshop.data.Book;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

    @Autowired
    private DataService dataServ;

    @RequestMapping(method = RequestMethod.GET)
    public String addBooksToPage(Model model) {
        List<Book> recentBooks = dataServ.findRandomBooks(6);
        List<Book> featuredBooks = dataServ.findRandomBooks(8);
        model.addAttribute("recentBooks", recentBooks);
        model.addAttribute("featuredBooks", featuredBooks);
        return "index";
    }
}
