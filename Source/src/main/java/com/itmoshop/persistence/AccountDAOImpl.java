package com.itmoshop.persistence;

import com.itmoshop.data.Account;
import com.itmoshop.data.AccountRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account findAccountById(long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public Account findAccountByEmail(String email) {
        Query query = entityManager.createQuery("SELECT a FROM Account a WHERE a.email LIKE :email");
        query.setParameter("email", email);
        return (Account) query.getSingleResult();
    }

    @Override
    public Account saveAccount(Account account) {

        if (account.getId() == 0) {
            entityManager.persist(account);
        } else {
            account = entityManager.merge(account);
        }

        return account;
    }

    @Override
    public void deleteAccount(Account account) {
        entityManager.remove(account);
    }

    @Override
    public int deleteAllAccountsExceptAdmin() {
        Query query = entityManager.createQuery("DELETE FROM Account a WHERE a.role NOT LIKE :role");
        query.setParameter("role", AccountRole.ADMIN);
        return query.executeUpdate();
    }
}
