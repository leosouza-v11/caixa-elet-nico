package com.cash.machine.api.classes;

import java.util.ArrayList;
import java.util.List;

import com.cash.machine.api.models.database.DatabaseAccount;

public class Session {
    
    private static Session instance;

    private List<DatabaseAccount> accounts = new ArrayList<DatabaseAccount>();

    private Session() { }

    public static Session getInstance() {
        if (Session.instance == null) {
            Session.instance = new Session();
        }
        
        return Session.instance;
    }

    public void startSession(DatabaseAccount account) {
        boolean sessionExists = false;

        for (DatabaseAccount account2 : this.accounts) {
            if (account.getAccount() == account2.getAccount()) {
                sessionExists = true;
                break;
            }
        }

        if (sessionExists) {
            throw new Error("Sessão já está aberta. Tente Novamente mais Tarde.");
        }

        this.accounts.add(account);
    }

    public void endSession(int id) {
        boolean isEnd = false;
        List<DatabaseAccount> newAccounts = new ArrayList<DatabaseAccount>();

        for (DatabaseAccount account : this.accounts) {
            if (account.getAccount() == id) {
                isEnd = true;
                continue;
            }

            newAccounts.add(account);
        }

        if (!isEnd) {
            throw new Error("Sessão não Poder ser Finalizada, Pois Não Foi Encontrada.");
        }

        this.accounts = newAccounts;
    }

    public DatabaseAccount getSession(int id) {
        DatabaseAccount account = null;

        for (DatabaseAccount account2 : this.accounts) {
            if (account2.getAccount() == id) {
                account = account2;
                break;
            }
        }

        if (account == null) {
            throw new Error("Nenhuma Sessão Encontrada com Código: " + id);
        }

        return account;
    }

}
