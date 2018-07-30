package com.paul.resources;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private Account a;
    private Account b;

    @Before
    public void setupAccounts() {
        a = new Account("John", "Doe", 10000f);
        b = new Account("Jane", "Doe", 10000f);
    }

    @Test
    public void testNormalTransfer() {
        double accountABalance = a.getBalance();
        double accountBBalance = b.getBalance();
        double amountToTransfer = 43;
        double expectedAmountInAccountA = accountABalance - amountToTransfer;
        double expectedAmountInAccountB = accountBBalance + amountToTransfer;
        boolean result = a.transferAmountToAccount(amountToTransfer, b);
        assertTrue("The transfer from account A to account B should have succeeded.", result);
        assertEquals("The correct amount of funds were not transferred from account A.", expectedAmountInAccountA, a.getBalance(), 0);
        assertEquals("The correct amount of funds were not transferred from account B.", expectedAmountInAccountB, b.getBalance(), 0);
    }

    @Test
    public void testMoreThanAvailableFundsTransfer() {
        double accountABalance = a.getBalance();
        double accountBBalance = b.getBalance();
        double amountToTransfer = accountABalance + 1;

        boolean result = a.transferAmountToAccount(amountToTransfer, b);

        assertFalse("The transfer from account A to account B should have failed because of insufficient funds.", result);
        assertEquals("The correct amount of funds were not transferred from account A.", accountABalance, a.getBalance(), 0);
        assertEquals("The correct amount of funds were not transferred from account B.", accountBBalance, b.getBalance(), 0);
    }

    @Test
    public void testExactAvailableFundsTransfer() {
        double accountBBalance = b.getBalance();
        double amountToTransfer = a.getBalance();
        double expectedAmountInAccountA = a.getBalance() - amountToTransfer;
        double expectedAmountInAccountB = accountBBalance + amountToTransfer;
        boolean result = a.transferAmountToAccount(amountToTransfer, b);

        assertTrue("The transfer from account A to account B should have succeeded because it was the exact amount of available funds..", result);
        assertEquals("The correct amount of funds were not transferred from account A.", expectedAmountInAccountA, a.getBalance(), 0);
        assertEquals("The correct amount of funds were not transferred from account B.", expectedAmountInAccountB, b.getBalance(), 0);

    }

    @Test
    public void testCorrectTransactions() {
        double accountABalance = a.getBalance();
        double accountBBalance = b.getBalance();
        double amountToTransfer = 43;
        double expectedAmountInAccountA = accountABalance - amountToTransfer;
        double expectedAmountInAccountB = accountBBalance + amountToTransfer;
        boolean result = a.transferAmountToAccount(amountToTransfer, b);
        Transaction depositTransactionFromA = a.getTransactions().get(0);
        Transaction transactionFromA = a.getTransactions().get(1);
        Transaction depositTransactionFromB = b.getTransactions().get(0);
        Transaction transactionFromB = b.getTransactions().get(1);

        //Testing transfer of amount
        assertTrue("The transfer from account A to account B should have succeeded.", result);
        assertEquals("The correct amount of funds were not transferred from account A.", expectedAmountInAccountA, a.getBalance(), 0);
        assertEquals("The correct amount of funds were not transferred from account B.", expectedAmountInAccountB, b.getBalance(), 0);

        //Testing events of transactions of account A
        assertEquals("The initial deposit transaction of account A is not of the correct type.", "C", depositTransactionFromA.getTransactionType());
        assertEquals("The transfer transaction of account A is not of the correct type.", "D", transactionFromA.getTransactionType());
        assertEquals("The transfer transaction of account B does not have the correct amount.", amountToTransfer, transactionFromA.getAmount(), 0);
        assertNotNull("Transaction of account A should not have a null date.", transactionFromA.getTransactionDate());

        //Testing events of transactions of account B
        assertEquals("The initial deposit transaction of account B is not of the correct type.", "C", depositTransactionFromB.getTransactionType());
        assertEquals("The transfer transaction of account B is not of the correct type.", "C", transactionFromB.getTransactionType());
        assertEquals("The transfer transaction of account B does not have the correct amount.", amountToTransfer, transactionFromB.getAmount(), 0);
        assertNotNull("Transaction of account B should not have a null date.", transactionFromB.getTransactionDate());
    }

    @Test
    public void testDefaultConstructor() {
        Account C = new Account();
        assertNotNull("The account should not be null after default constructor.", C);
        assertNotNull("The account's person should not be null after the default constructor.", C.getPerson());
        assertNotNull("The account's transactions should not be null after the default constructor.", C.getTransactions());
    }

    @Test
    public void getAccountNumber() {
        long AccountNumberA = a.getAccountNumber();
        if (AccountNumberA <= 0L) {
            fail("The random initializer did not work. " + a.getAccountNumber());
        }
    }

    @Test
    public void testToStringMethod() {
        String expectedString = "The account of John Doe has a balance of 10000.00";
        assertEquals("The toString method does not print what is expected.", expectedString, a.toString());
    }
}