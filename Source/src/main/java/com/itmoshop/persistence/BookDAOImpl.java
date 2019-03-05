package com.itmoshop.persistence;

import com.itmoshop.data.Book;
import com.itmoshop.data.BookReview;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book findBookById(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        Query query = entityManager.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author");
        query.setParameter("author", "%" + author + "%");
        return (List<Book>) query.getResultList();
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        Query query = entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title");
        query.setParameter("title", "%" + title + "%");
        return (List<Book>) query.getResultList();
    }

    @Override
    public List<Book> findBooksByTitleOrAuthorOrPublisherOrPublishDate(String param) {
        Query query = entityManager.createQuery("SELECT b FROM Book b " +
                "WHERE b.title LIKE :param " +
                "OR b.author LIKE :param " +
                "OR b.publisher LIKE :param " +
                "OR b.publishDate LIKE :param");
        query.setParameter("param", "%" + param + "%");
        return (List<Book>) query.getResultList();
    }

    @Override
    public List<Book> findBooksByPrice(double price) {
        Query query = entityManager.createQuery("SELECT b FROM Book b WHERE b.price LIKE :price");
        query.setParameter("price", price);
        return (List<Book>) query.getResultList();
    }

    @Override
    public List<Book> findRandomBooks(int howMany) {
        Query count = entityManager.createQuery("SELECT COUNT(b.id) FROM Book b");
        Query query = entityManager.createQuery("SELECT b FROM Book b");
        long numOfBooks = (long) count.getSingleResult();

        if (numOfBooks == 0) {
            return null;
        }

        long firstId = ((Book) query.setMaxResults(1).getResultList().get(0)).getId();


        Set<Long> randomBookIdsToFind = new HashSet<>();
        Random random = new Random();
        long sentinel = numOfBooks > howMany
                ? howMany
                : numOfBooks;
        while (randomBookIdsToFind.size() < sentinel) {
            randomBookIdsToFind.add((long) random.nextInt((int)numOfBooks) + firstId);
        }
        List<Book> resultList = new ArrayList<>();
        for (Long id : randomBookIdsToFind) {
            resultList.add(findBookById(id));
        }
        return resultList;
    }

    @Override
    public List<Integer> findBooksPublishDates() {
        Query query = entityManager.createQuery("SELECT DISTINCT b.publishDate FROM Book b");
        List<Integer> resultList = new ArrayList<>();
        for (Date d : (List<Date>) query.getResultList()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            resultList.add(calendar.get(Calendar.YEAR));
        }

        Collections.sort(resultList, Collections.reverseOrder());
        return resultList;
    }

    @Override
    public void addBookReview(long bookId, BookReview bookReview){
        Book book = findBookById(bookId);
        bookReview.setBook(book);
        book.addReview(bookReview);
    }

    @Override
    public Book saveBook(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
        } else {
            book = entityManager.merge(book);
        }
        return book;
    }

    @Override
    public void deleteBook(Book book) {
        entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
    }

    @Override
    public int deleteAllBooks() {
        Query deleteBookReviews = entityManager.createQuery("DELETE FROM BookReview br");
        deleteBookReviews.executeUpdate();
        Query deleteBooks = entityManager.createQuery("DELETE FROM Book b");
        return deleteBooks.executeUpdate();
    }
}
