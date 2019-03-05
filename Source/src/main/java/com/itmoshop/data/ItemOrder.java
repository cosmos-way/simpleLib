package com.itmoshop.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "orders")
public class ItemOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    private boolean newOrder = true;
    private boolean confirmedOrder;
    @ManyToMany(mappedBy="ordersSet", fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="order_books")
    private Map<Long, Integer> orderedBooks = new HashMap<>();
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDateTime;
    private double orderSum;

    public ItemOrder() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNewOrder() {
        return newOrder;
    }

    public void setNewOrder(boolean isNewOrder) {
        this.newOrder = isNewOrder;
    }

    public boolean isConfirmedOrder() {
        return confirmedOrder;
    }

    public void setConfirmedOrder(boolean isConfirmedOrder) {
        this.confirmedOrder = isConfirmedOrder;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public double getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(double orderSum) {
        this.orderSum = orderSum;
    }

    public void addToOrderSum(double itemPrice, int quantity) {
        this.orderSum += itemPrice * quantity;
    }

    public void subtractFromOrderSum(double itemPrice, int quantity) {
        this.orderSum -= itemPrice * quantity;
    }

    public Map<Long, Integer> getOrderedBooks() {
        return orderedBooks;
    }

    public void setOrderedBooks(Map<Long, Integer> orderedBooks) {
        this.orderedBooks = orderedBooks;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemOrder itemOrder = (ItemOrder) o;

        if (id != itemOrder.id) return false;
        if (newOrder != itemOrder.newOrder) return false;
        if (confirmedOrder != itemOrder.confirmedOrder) return false;
        return Double.compare(itemOrder.orderSum, orderSum) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (newOrder ? 1 : 0);
        result = 31 * result + (confirmedOrder ? 1 : 0);
        temp = Double.doubleToLongBits(orderSum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
