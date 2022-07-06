package com.cash.machine.api.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cash.machine.api.interfaces.DatabaseCrud;
import com.cash.machine.api.models.database.DatabaseTransaction;

public class TransactionController implements DatabaseCrud<DatabaseTransaction> {

    private static TransactionController instance;

    private List<DatabaseTransaction> transactions = new ArrayList<DatabaseTransaction>();

    private int id = 0;

    private TransactionController() { }

    public static TransactionController getInstance() {
        if (TransactionController.instance == null) {
            TransactionController.instance = new TransactionController();
        }

        return TransactionController.instance;
    }

    public static void destroyInstance() {
        TransactionController.instance = null;
    }

    //funcao para formatacao de data e hora
    private String getDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    //utilizacao do crud
    @Override
    public int insert(DatabaseTransaction value) {
        value.setId(++this.id).setDateTime(this.getDateTime());
        this.transactions.add(value);
        return this.id;
    }

    @Override
    public DatabaseTransaction update(DatabaseTransaction value) {
        boolean isUpdate = false;
        int indexUpdate = -1;

        for (int index = 0; index < this.transactions.size(); index++) {
            if (this.transactions.get(index).getId() == value.getId()) {
                value.setDateTime(this.getDateTime());
                this.transactions.set(index, value);
                indexUpdate = index;
                isUpdate = true;
                break;
            }
        }

        if (!isUpdate) {
            throw new Error("Nenhuma Transação Encontrada com Código: " + value.getAccount());
        }

        return this.transactions.get(indexUpdate);
    }

    @Override
    public void delete(int id) {
        throw new Error("Transação Não Pode Ser Deletada.");
    }

    @Override
    public List<DatabaseTransaction> getAll() {
        throw new Error("Transação Não Pode Ser Listada.");
    }

    @Override
    public DatabaseTransaction getDataByID(int id) {
        DatabaseTransaction transaction = null;
        for (DatabaseTransaction transaction2 : this.transactions) {
            if (transaction2.getId() == id) {
                transaction = transaction2;
                break;
            }
        }

        if (transaction == null) {
            throw new Error("Nenhuma Transação Encontrada com Código: " + id);
        }

        return transaction;
    }

    public List<DatabaseTransaction> getAllDataByAccount(int account) {
        List<DatabaseTransaction> transactionsAccount = new ArrayList<DatabaseTransaction>();

        for (DatabaseTransaction transaction : this.transactions) {
            if (transaction.getAccount() == account) {
                transactionsAccount.add(transaction);
            }
        }

        return transactionsAccount;
    }
    
}
