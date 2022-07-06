package com.cash.machine.api.models.database;

public class DatabaseAccount {

    private int account;

    private String password;

    private String username;

    private double availableBalance;

    private DatabaseAccount() { }

    //design patterns build utilizado para a criação da classe databaseaccounts, builder é o construtor da classe. 
    // o builder ele permite que voce reproduza os mesmos codigos de construcao para diferentes objetos
    public static DatabaseAccount build() {
        return new DatabaseAccount();
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public DatabaseAccount setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DatabaseAccount setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DatabaseAccount setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAccount() {
        return account;
    }

    public DatabaseAccount setAccount(int account) {
        this.account = account;
        return this;
    }

}
