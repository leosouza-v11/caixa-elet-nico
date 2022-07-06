package com.cash.machine.api.models.database;

public class DatabaseTransaction {
    private int id;

    private int account;

    private int targetAccount;

    private double availableBalance;

    private double availableBalanceAfeterOperation;

    private double operationValue;

    private String operation;

    private String dateTime;

    private String status;

    private String log;

    private DatabaseTransaction() { }

    public static DatabaseTransaction build() {
        return new DatabaseTransaction();
    }

    public double getAvailableBalanceAfeterOperation() {
        return availableBalanceAfeterOperation;
    }

    public DatabaseTransaction setAvailableBalanceAfeterOperation(double availableBalanceAfeterOperation) {
        this.availableBalanceAfeterOperation = availableBalanceAfeterOperation;
        return this;
    }


    public double getOperationValue() {
        return operationValue;
    }

    public DatabaseTransaction setOperationValue(double operationValue) {
        this.operationValue = operationValue;
        return this;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public DatabaseTransaction setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
        return this;
    }

    public String getLog() {
        return log;
    }

    public DatabaseTransaction setLog(String log) {
        this.log = log;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DatabaseTransaction setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public DatabaseTransaction setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public DatabaseTransaction setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    public DatabaseTransaction setTargetAccount(int targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    public int getAccount() {
        return account;
    }

    public DatabaseTransaction setAccount(int account) {
        this.account = account;
        return this;
    }

    public int getId() {
        return id;
    }

    public DatabaseTransaction setId(int id) {
        this.id = id;
        return this;
    }
}
