package com.paul.resources;


import java.util.ArrayList;
import java.util.Random;

public class Account {

    private final long accountNumber;

    private final Person person;
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private double balance;

    Account() {
        this.person = new Person();
        accountNumber = generateAccountNumber();
    }

    public Account(String name, String lastname, double amount) {
        this.person = new Person(name, lastname);
        accountNumber = generateAccountNumber();
        this.depositAmount(amount);
    }

    @Override
    public String toString() {
        return String.format("The account of " + this.person + " has a balance of %.02f", balance);
    }

    private void depositAmount(double amount) {
        this.balance += amount;
        this.addCreditTransaction(amount);
    }

    private boolean withdrawAmount(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            this.addDebitTransaction(amount);
            return true;
        } else {
            return false;
        }
    }

    private void addCreditTransaction(double amount) {
        Transaction transaction = new Transaction(amount, "C");
        this.transactions.add(transaction);
    }

    private void addDebitTransaction(double amount) {
        Transaction transaction = new Transaction(amount, "D");
        this.transactions.add(transaction);
    }

    public synchronized boolean transferAmountToAccount(double amount, Account otherAccount) {
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

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    private long generateAccountNumber() {
        return Math.abs(new Random().nextLong());
    }
}