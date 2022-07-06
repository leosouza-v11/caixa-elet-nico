package com.cash.machine.api.models.response;

public class TransactionResponse {
    private int id;

    private int account;

    private String accountUsername;

    private int targetAccount;

    private String targetAccountUsername;

    private double availableBalance;

    private double availableBalanceAfeterOperation;

    private double operationValue;

    private String operation;

    private String dateTime;

    private String status;

    private String log;

    private TransactionResponse() { }

    public static TransactionResponse build() {
        return new TransactionResponse();
    }

    public String getTargetAccountUsername() {
        return targetAccountUsername;
    }

    public TransactionResponse setTargetAccountUsername(String targetAccountUsername) {
        this.targetAccountUsername = targetAccountUsername;
        return this;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public TransactionResponse setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
        return this;
    }

    public double getAvailableBalanceAfeterOperation() {
        return availableBalanceAfeterOperation;
    }

    public TransactionResponse setAvailableBalanceAfeterOperation(double availableBalanceAfeterOperation) {
        this.availableBalanceAfeterOperation = availableBalanceAfeterOperation;
        return this;
    }


    public double getOperationValue() {
        return operationValue;
    }

    public TransactionResponse setOperationValue(double operationValue) {
        this.operationValue = operationValue;
        return this;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public TransactionResponse setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public String getLog() {
        return log;
    }

    public TransactionResponse setLog(String log) {
        this.log = log;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransactionResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public TransactionResponse setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public TransactionResponse setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    public TransactionResponse setTargetAccount(int targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    public int getAccount() {
        return account;
    }

    public TransactionResponse setAccount(int account) {
        this.account = account;
        return this;
    }

    public int getId() {
        return id;
    }

    public TransactionResponse setId(int id) {
        this.id = id;
        return this;
    }
}
