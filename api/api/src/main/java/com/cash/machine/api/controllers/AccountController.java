package com.cash.machine.api.controllers;

import java.util.ArrayList;
import java.util.List;

import com.cash.machine.api.interfaces.DatabaseCrud;
import com.cash.machine.api.models.database.DatabaseAccount;

public class AccountController implements DatabaseCrud<DatabaseAccount> {
    
	
	//segundo design patterns singleton, ele constroi a classe uma vez e guarda a instancia dentro da variavel estatica e privada
    private static AccountController instance;

    private List<DatabaseAccount> accounts = new ArrayList<DatabaseAccount>();

    private int id = 0;

    private AccountController() { }

    //como nao temos banco de dados a gente usa o singleton para que nao percamos a lista de contas criadas (databaseaccount)
    //entao nessa funcao ele verifica se a instancia for nula ele cria, senao ele contnua retornando a mesma instancia
    public static AccountController getInstance() {
        if (AccountController.instance == null) {
            AccountController.instance = new AccountController();
        }

        return AccountController.instance;
    }

    //seta instancia pra nulo e perde todos os dados, tira a classe da memoria, no caso
    public static void destroyInstance() {
        AccountController.instance = null;
    }

    @Override
    public int insert(DatabaseAccount value) {
        value.setAccount(++this.id);
        this.accounts.add(value);
        return this.id;
    }

    @Override
    public DatabaseAccount update(DatabaseAccount value) {
        boolean isUpdate = false;
        int indexUpdate = -1;

        for (int index = 0; index < this.accounts.size(); index++) {
            if (this.accounts.get(index).getAccount() == value.getAccount()) {
                this.accounts.set(index, value);
                indexUpdate = index;
                isUpdate = true;
                break;
            }
        }

        if (!isUpdate) {
            throw new Error("Nenhuma Conta Encontrada com Código: " + value.getAccount());
        }

        return this.accounts.get(indexUpdate);
    }

    @Override
    public void delete(int id) {
        boolean isDelete = false;

        List<DatabaseAccount> newAccounts = new ArrayList<DatabaseAccount>();

        for (DatabaseAccount account : this.accounts) {
            if (account.getAccount() == id) {
                isDelete = true;
                continue;
            }

            newAccounts.add(account);
        }

        if (!isDelete) {
            throw new Error("Nenhuma Conta Encontrada com Código: " + id);
        }

        this.accounts = newAccounts;
    }

    @Override
    public List<DatabaseAccount> getAll() {
        return this.accounts;
    }

    @Override
    public DatabaseAccount getDataByID(int id) {
        DatabaseAccount account = null;
        for (DatabaseAccount account2 : this.accounts) {
            if (account2.getAccount() == id) {
                account = account2;
                break;
            }
        }

        if (account == null) {
            throw new Error("Nenhuma Conta Encontrada com Código: " + id);
        }

        return account;
    }

}
