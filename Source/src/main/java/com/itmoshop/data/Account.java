package com.itmoshop.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;
    @Enumerated(EnumType.STRING)
    private AccountRole role;
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable(name = "accounts_orders",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")}
    )
    private Set<ItemOrder> ordersSet = new HashSet<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookRequest> requests = new ArrayList<>();

    public List<BookRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<BookRequest> requests) {
        this.requests = requests;
    }

    public Account() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = Enum.valueOf(AccountRole.class, role);
    }

    public Set<ItemOrder> getOrdersSet() {
        return ordersSet;
    }

    public void setOrdersSet(Set<ItemOrder> ordersSet) {
        this.ordersSet = ordersSet;
    }

    public void addOrderToOrdersSet(ItemOrder itemOrder) {
        this.ordersSet.add(itemOrder);
        itemOrder.getAccounts().add(this);
    }

    public void removeOrderFromOrdersSet(ItemOrder itemOrder) {
        this.ordersSet.remove(itemOrder);
        itemOrder.getAccounts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) return false;
        if (lastName != null ? !lastName.equals(account.lastName) : account.lastName != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (mobile != null ? !mobile.equals(account.mobile) : account.mobile != null) return false;
        return role == account.role;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
