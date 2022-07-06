package com.cash.machine.api.models.request;

public class RequestLogin {
    
    private int account;

    private String password;

    private RequestLogin() { }

    public static RequestLogin build() {
        return new RequestLogin();
    }

    public String getPassword() {
        return password;
    }

    public RequestLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAccount() {
        return account;
    }

    public RequestLogin setAccount(int account) {
        this.account = account;
        return this;
    }

}
