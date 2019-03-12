package com.itmoshop.persistence;

import com.itmoshop.data.BookRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<BookRequest> findRequests(long limit,
                                          String status,
                                          long id,
                                          long userId,
                                          long bookId,
                                          String dateFrom,
                                          String dateTill,
                                          int expired){
        int i=0;

        System.out.println(limit);
        System.out.println( status);
        System.out.println( id);
        System.out.println( userId);
        System.out.println( bookId);
        System.out.println( dateFrom);
        System.out.println( dateTill);
        System.out.println( expired);

        System.out.println(i++);
        String queryStr = new String("");
        if(!status.equals("null"))
            queryStr += "AND b.status LIKE '"+status+"' ";
        System.out.println(i++);
        if(id != -1)
            queryStr += "AND b.request_id LIKE "+id+" ";
        System.out.println(i++);
        if(userId != -1)
            queryStr += "AND b.id LIKE "+userId+" ";
        System.out.println(i++);
        if(bookId != -1)
            queryStr += "AND book_id LIKE "+bookId+" ";
        System.out.println(i++);
        if(!dateFrom.equals("null"))
            queryStr += "AND b.orderDateTime LIKE '"+dateFrom+"%' ";
        System.out.println(i++);
        if(!dateTill.equals("null"))
            queryStr += "AND b.dateTill LIKE '"+dateTill+"%' ";
        System.out.println(i++);
        queryStr = queryStr.replaceFirst("AND", "WHERE");

        System.out.println(i++);



        System.out.println(i++);

//        Query query = entityManager.createQuery("SELECT b FROM bookRequest b " +
//                "WHERE b.title LIKE :param " +
//                "OR b.author LIKE :param " +
//                "OR b.publisher LIKE :param " +
//                "OR b.publishDate LIKE :param"
//
//        );

        Query query = entityManager.createQuery("SELECT b FROM BookRequest b " +queryStr);
        System.out.println(queryStr+"hehedvkjnskdjvnsnv");

        //query.setParameter("param", "%" + param + "%");
        if(limit>0)
            query.setMaxResults((int)limit);
        else
            query.setMaxResults(10);
        return (List<BookRequest>) query.getResultList();
    }


    @Override
    public boolean findActiveRequest(long userId,long bookId){
        Query query = entityManager.createQuery("SELECT request_id FROM BookRequest WHERE " +
                "(status not like 'CANCELED') and (status not like 'ERROR')" +
                " and (status not like 'DELIVERED') and (book_id LIKE "+bookId+") and (id LIKE "+ userId +")");

        List<BookRequest> tmp = query.getResultList();

        System.out.println(tmp);
        System.out.println(tmp.isEmpty());

        if (tmp.isEmpty())
            return false;
        else
            return true;
    }
}
