package com.paul.resources;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void getAmount() {
        Transaction t = new Transaction(45f,"D");
        assertEquals("The amount is not correctly assigned in the transaction",45f,t.getAmount(),0);
    }

    @Test
    public void getTransactionType() {
        Transaction t = new Transaction(45f,"D");
        assertEquals("The transaction type is not correctly assigned in the transaction","D",t.getTransactionType());
        t = new Transaction(45f,"C");
        assertEquals("The transaction type is not correctly assigned in the transaction","C",t.getTransactionType());
    }

    @Test
    public void getTransactionDate() {
        Transaction t = new Transaction(45f,"D");
        LocalDateTime now = LocalDateTime.now();

        assertEquals("The day of the month of the created transaction does not match the correct time.",now.getDayOfMonth(),t.getTransactionDate().getDayOfMonth());
        assertEquals("The month of the created transaction does not match the correct time.",now.getMonth(),t.getTransactionDate().getMonth());
        assertEquals("The year of the created transaction does not match the correct time.",now.getYear(),t.getTransactionDate().getYear());
        assertEquals("The hour of the created transaction does not match the correct time.",now.getHour(),t.getTransactionDate().getHour());
    }
}