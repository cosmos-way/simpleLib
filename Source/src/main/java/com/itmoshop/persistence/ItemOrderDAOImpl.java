package com.itmoshop.persistence;

import com.itmoshop.data.ItemOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ItemOrderDAOImpl implements ItemOrderDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ItemOrder findItemOrderById(long id) {
        Query query = entityManager.createQuery("SELECT io FROM ItemOrder io WHERE io.id LIKE :id");
        query.setParameter("id", id);
        return (ItemOrder)query.getSingleResult();
    }

    @Override
    public ItemOrder saveItemOrder(ItemOrder itemOrder) {
        if (itemOrder.getId() == 0) {
            entityManager.persist(itemOrder);
        } else {
            itemOrder = entityManager.merge(itemOrder);
        }
        return itemOrder;
    }

    @Override
    public void deleteItemOrder(ItemOrder itemOrder) {
        entityManager.remove(entityManager.find(ItemOrder.class, itemOrder.getId()));
    }

    @Override
    public int deleteAllItemOrders() {
        Query clearConstraintsTableQuery = entityManager.createNativeQuery("DELETE FROM order_books");
        clearConstraintsTableQuery.executeUpdate();
        Query query = entityManager.createQuery("DELETE FROM ItemOrder io");
        return query.executeUpdate();
    }
}
