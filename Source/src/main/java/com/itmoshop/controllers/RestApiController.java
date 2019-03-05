package com.itmoshop.controllers;

import com.itmoshop.data.Book;
import com.itmoshop.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private DataService dataServ;

    @RequestMapping(value = "/book/get/{bookId}",
            method = RequestMethod.GET)
    public Book sendBookAsResource(@PathVariable long bookId) {
        return dataServ.findBookById(bookId);
    }

    @RequestMapping(value = "/book/add",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<Book> saveBookAsResource(
            @RequestBody Book book,
            UriComponentsBuilder ucb
    ) {
        book = dataServ.saveBook(book);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI resourceLocation = ucb.path("/single/")
                .path(String.valueOf(book.getId()))
                .build().toUri();
        httpHeaders.setLocation(resourceLocation);

        return new ResponseEntity<Book>(book, httpHeaders, HttpStatus.CREATED);
    }
}
