package com.itmoshop.persistence;

import com.itmoshop.data.BookRequest;

import java.util.List;

public interface RequestDAO {
    BookRequest findRequestById(long id);
    BookRequest saveRequest(BookRequest bookRequest);
    void deleteRequest(BookRequest bookRequest);
    int deleteAllRequests();
    List<BookRequest> findRequests(long limit,
                                   String status,
                                   long id,
                                   long userId,
                                   long bookId,
                                   String dateFrom,
                                   String dateTill,
                                   int expired);
    boolean findActiveRequest(long userId,long bookId);
}
