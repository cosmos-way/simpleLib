package com.itmoshop.controllers;

import com.itmoshop.data.Account;
import com.itmoshop.data.Book;
import com.itmoshop.data.BookReview;
import com.itmoshop.services.AdminService;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/single")
public class SingleController {

    @Autowired
    private DataService dataServ;

    @Autowired
    private AdminService admServ;

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public String showBook(Model model,
                           @PathVariable long bookId) {
        Book book = dataServ.findBookById(bookId);
        List<BookReview> reviews = book.getReviews();
        model.addAttribute("book", book);
        model.addAttribute("bookReviews", reviews);

        List<Book> featuredBooks = dataServ.findRandomBooks(15);
        model.addAttribute("featuredBooks", featuredBooks);
        return "single";
    }

    @RequestMapping(value="/remove", method=RequestMethod.POST)
    public String removeBook(@RequestParam("bookId") long bookId){
        Book book = dataServ.findBookById(bookId);
        dataServ.deleteBook(book);
        return "redirect:/index";
}

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String addBookReview(@RequestParam("bookId") long bookId,
                                @RequestParam("bookReviewText") String bookReviewText,
                                Principal principal,
                                Model model) {
        Account reviewPoster = admServ.findAccountByEmail(principal.getName());

        BookReview bookReview = new BookReview(null,
                reviewPoster.getFirstName(),
                bookReviewText, new Date());

        dataServ.addBookReview(bookId, bookReview);
        Book book = dataServ.findBookById(bookId);

        model.addAttribute("book", book);
        model.addAttribute("bookReviews", book.getReviews());

        List<Book> featuredBooks = dataServ.findRandomBooks(15);
        model.addAttribute("featuredBooks", featuredBooks);
        return "single";
    }
}
