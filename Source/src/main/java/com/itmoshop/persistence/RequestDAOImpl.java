package com.itmoshop.persistence;

import com.itmoshop.data.BookRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RequestDAOImpl implements RequestDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookRequest findRequestById(long id) {
        Query query = entityManager.createQuery("SELECT io FROM BookRequest io WHERE io.id LIKE :id");
        query.setParameter("id", id);
        return (BookRequest)query.getSingleResult();
    }

    @Override
    public BookRequest saveRequest(BookRequest bookRequest) {
        if (bookRequest.getId() == 0) {
            entityManager.persist(bookRequest);
        } else {
            bookRequest = entityManager.merge(bookRequest);
        }
        return bookRequest;
    }

    @Override
    public void deleteRequest(BookRequest bookRequest) {
        entityManager.remove(entityManager.find(BookRequest.class, bookRequest.getId()));
    }

    @Override
    public int deleteAllRequests() {
        Query clearConstraintsTableQuery = entityManager.createNativeQuery("DELETE FROM order_books");
        clearConstraintsTableQuery.executeUpdate();
        Query query = entityManager.createQuery("DELETE FROM bookRequest io");
        return query.executeUpdate();
    }


}
