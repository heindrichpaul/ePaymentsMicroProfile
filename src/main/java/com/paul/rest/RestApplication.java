package com.paul.rest;

import com.paul.resources.Account;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;

@ApplicationPath("ePayments")
public class RestApplication extends Application {
    static final HashMap<Long, Account> accounts = new HashMap<>();
}