package com.paul.rest;

import com.paul.resources.Account;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("accounts")
public class AccountResource {

    private HashMap<Long, Account> accounts = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAccounts() {

        JsonObjectBuilder builder = Json.createObjectBuilder();


        System.getProperties()
                .entrySet()
                .forEach(entry -> builder.add((String) entry.getKey(),
                        (String) entry.getValue()));

        return builder.build();
    }
}