package com.cash.machine.api.models.response;

public class AccountResponse {
    private int account;

    private String username;

    private double availableBalance;

    private AccountResponse() { }

    public static AccountResponse build() {
        return new AccountResponse();
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public AccountResponse setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AccountResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getAccount() {
        return account;
    }

    public AccountResponse setAccount(int account) {
        this.account = account;
        return this;
    }
}
