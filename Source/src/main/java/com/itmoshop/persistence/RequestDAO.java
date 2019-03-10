package com.itmoshop.persistence;

import com.itmoshop.data.BookRequest;

public interface RequestDAO {
    BookRequest findRequestById(long id);
    BookRequest saveRequest(BookRequest bookRequest);
    void deleteRequest(BookRequest bookRequest);
    int deleteAllRequests();
}
