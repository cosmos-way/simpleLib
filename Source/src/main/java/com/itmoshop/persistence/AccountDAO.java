package com.itmoshop.persistence;

import com.itmoshop.data.Account;

public interface AccountDAO {
    Account findAccountById(long id);
    Account findAccountByEmail(String email);
    Account saveAccount(Account account);
    void deleteAccount(Account account);
    int deleteAllAccountsExceptAdmin();
}
