package com.paul.resources;


import java.util.ArrayList;
import java.util.Random;

public class Account {

    private final long accountNumber = new Random().nextLong();

    private final Person person;
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private float balance;

    public Account() {
        this.person = new Person();
    }

    public Account(String name, String lastname, float amount) {
        this.person = new Person(name, lastname);
        this.depositAmount(amount);
    }

    @Override
    public String toString() {
        return String.format("The account of " + this.person + " has a balance of %.02f", balance);
    }

    private void depositAmount(float amount) {
        this.balance += amount;
        this.addCreditTransaction(amount);
    }

    private boolean withdrawAmount(float amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            this.addDebitTransaction(amount);
            return true;
        } else {
            return false;
        }
    }

    private void addCreditTransaction(float amount) {
        Transaction transaction = new Transaction(amount, "C");
        this.transactions.add(transaction);
    }

    private void addDebitTransaction(float amount) {
        Transaction transaction = new Transaction(amount, "D");
        this.transactions.add(transaction);
    }

    public boolean transferAmountToAccount(float amount, Account otherAccount) {
        if (this.withdrawAmount(amount)) {
            otherAccount.depositAmount(amount);
            return true;
        } else {
            return false;
        }
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Person getPerson() {
        return person;
    }

    public float getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}