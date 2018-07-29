package com.paul.resources;


import java.time.LocalDateTime;

class Transaction {
    private final float amount;
    private final String transactionType;
    private final LocalDateTime transactionDate;

    public Transaction(float amount, String transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public float getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
