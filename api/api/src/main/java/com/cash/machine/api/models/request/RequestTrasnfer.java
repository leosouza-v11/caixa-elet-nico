package com.cash.machine.api.models.request;

public class RequestTrasnfer {
    private int targetAccount;
    
    private double value;

    private RequestTrasnfer() { }

    public static RequestTrasnfer build() {
        return new RequestTrasnfer();
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    public double getValue() {
        return value;
    }

    public RequestTrasnfer setValue(double value) {
        this.value = value;
        return this;
    }

    public RequestTrasnfer setTargetAccount(int targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

}
