package com.paul.rest;

import com.paul.resources.Account;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;

import static com.paul.rest.RestApplication.accounts;

@Path("accounts")
public class AccountResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAccounts() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        accounts.values().forEach(e -> arrayBuilder.add(
                objBuilder.add("accountNumber", e.getAccountNumber())
                        .add("balance", e.getBalance())
                        .add("ownerName", e.getPerson().toString())
                        .build()
                )
        );

        return objBuilder.add("accounts", arrayBuilder.build()).build();

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByNumber(@PathParam("id") Long id) {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        if (!accounts.containsKey(id)) {
            return Response.status(Response.Status.NOT_FOUND).entity(objBuilder.build()).build();
        } else {
            Account account = accounts.get(id);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            account.getTransactions().forEach(e -> arrayBuilder.add(
                    objBuilder.add("transactionDate", e.getTransactionDate().format(formatter))
                            .add("transactionType", e.getTransactionType())
                            .add("transactionAmount", e.getAmount())
                            .build()
                    )
            );

            JsonArray transactionArray = arrayBuilder.build();

            return Response.status(Response.Status.OK).entity(objBuilder.add("accountNumber", account.getAccountNumber())
                    .add("balance", account.getBalance())
                    .add("ownerName", account.getPerson().toString())
                    .add("transactions", transactionArray)
                    .build()).build();
        }

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response acceptJsonObject(JsonObject payload) {
        if (!(payload.containsKey("name") || payload.containsKey("lastname") || payload.containsKey("amount"))) {
            return Response.noContent().status(Response.Status.PARTIAL_CONTENT).build();
        }
        Account acc = new Account(payload.getString("name"), payload.getString("lastname"), payload.getJsonNumber("amount").doubleValue());
        if (!accounts.containsKey(acc.getAccountNumber())) {
            accounts.put(acc.getAccountNumber(), acc);
            return Response.noContent().status(Response.Status.CREATED).build();
        } else {
            return Response.noContent().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}