package com.itmoshop.controllers;

import com.itmoshop.data.Book;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private DataService dataServ;

    @RequestMapping(method = RequestMethod.GET)
    public String findBooks(Model model,
            @RequestParam("q") String searchCriteria) {
        List<Book> booksFound = dataServ.findBooksByTitleOrAuthorOrPublisher(searchCriteria);
//        List<Book> featuredBooks = dataServ.findRandomBooks(8);
//        List<Integer> booksPublishDates = dataServ.findYearsOfBooks();

        //model.addAttribute("featuredBooks", featuredBooks);
        model.addAttribute("booksFound", booksFound);
        //model.addAttribute("booksPublishDates", booksPublishDates);
        return "products";
    }
}
