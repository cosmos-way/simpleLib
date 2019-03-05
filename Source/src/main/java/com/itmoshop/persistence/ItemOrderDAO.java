package com.itmoshop.persistence;

import com.itmoshop.data.ItemOrder;

public interface ItemOrderDAO {
    ItemOrder findItemOrderById(long id);
    ItemOrder saveItemOrder(ItemOrder itemOrder);
    void deleteItemOrder(ItemOrder itemOrder);
    int deleteAllItemOrders();
}
