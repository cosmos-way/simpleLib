package com.itmoshop.persistence;

import com.itmoshop.data.Book;
import com.itmoshop.data.BookReview;

import java.util.List;

public interface BookDAO {
    Book findBookById(long id);
    List<Book> findBooksByAuthor(String author);
    List<Book> findBooksByTitle(String title);
    List<Book> findBooksByTitleOrAuthorOrPublisherOrPublishDate(String param);
    List<Book> findBooksByPrice(double price);
    List<Book> findRandomBooks(int howMany);
    List<Integer> findBooksPublishDates();
    void addBookReview(long bookId, BookReview bookReview);
    Book saveBook(Book book);
    void deleteBook(Book book);
    int deleteAllBooks();
}
