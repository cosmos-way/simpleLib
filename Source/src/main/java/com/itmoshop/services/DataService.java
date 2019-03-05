package com.itmoshop.services;

import com.itmoshop.data.Book;
import com.itmoshop.data.BookReview;
import com.itmoshop.persistence.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private BookDAO bookDAO;

    @Transactional
    public Book findBookById(long id) {
        return bookDAO.findBookById(id);
    }

    @Transactional
    public List<Book> findBooksByAuthor(String author) {
        return bookDAO.findBooksByAuthor(author);
    }

    @Transactional
    public List<Book> findBooksByTitle(String title) {
        return bookDAO.findBooksByTitle(title);
    }

    @Transactional
    public List<Book> findBooksByTitleOrAuthorOrPublisher(String param) {
        return bookDAO.findBooksByTitleOrAuthorOrPublisherOrPublishDate(param);
    }

    @Transactional
    public List<Book> findBooksByPrice(double price) {
        return bookDAO.findBooksByPrice(price);
    }

    @Transactional
    public List<Book> findRandomBooks(int howMany) {
        return bookDAO.findRandomBooks(howMany);
    }

    @Transactional
    public List<Integer> findYearsOfBooks() {
        return bookDAO.findBooksPublishDates();
    }

    @Transactional
    public void addBookReview(long bookId, BookReview bookReview) {
        bookDAO.addBookReview(bookId, bookReview);
    }

    @Transactional
    public Book saveBook(Book book) {
        return bookDAO.saveBook(book);
    }

    @Transactional
    public void deleteBook(Book book) {
        bookDAO.deleteBook(book);
    }

}
