package com.paul.resources;


import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;

public class Transaction {
    private final double amount;
    private final String transactionType;
    private final LocalDateTime transactionDate;

    @JsonbCreator
    Transaction(@JsonbProperty("transactionAmount") double amount, @JsonbProperty("transactionType") String transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
