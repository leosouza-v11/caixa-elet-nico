package com.cash.machine.api.models.request;

public class RequestRegister {
    private String username;

    private String password;

    private RequestRegister() { }

    public static RequestRegister build() {
        return new RequestRegister();
    }

    public String getPassword() {
        return password;
    }

    public RequestRegister setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RequestRegister setUsername(String username) {
        this.username = username;
        return this;
    }
}
